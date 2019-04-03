package org.nbone.spring.cloud.netflix.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import feign.slf4j.Slf4jLogger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import static feign.Util.UTF_8;
import static feign.Util.valuesOrEmpty;

/**
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2017/9/12
 */
@Configuration
public class FeignClientConfig {

    public static void main(String[] args) {
        System.out.println(Logger.Level.NONE.ordinal());
        System.out.println(Logger.Level.BASIC.ordinal());
        System.out.println(Logger.Level.HEADERS.ordinal());
        System.out.println(Logger.Level.FULL.ordinal());

    }

    @Autowired(required = false)
    private Logger logger;

    @Bean
    public FeignLoggerFactory feignLoggerFactory() {
        return new FeignLoggerFactoryx(logger);
    }


    @Bean
    public Logger.Level feignLoggerLevel() {

        return feign.Logger.Level.FULL;
    }


}


/**
 * 
 * @author chenyicheng
 * @version 1.0
 * @since 2018/1/19
 */

class FeignLoggerFactoryx implements FeignLoggerFactory {

    private Logger logger;

    public FeignLoggerFactoryx(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger create(Class<?> type) {
        return this.logger != null ? this.logger : new Slf4jLoggerx(type);
    }

}


/**
 * 
 * @author chenyicheng
 * @version 1.0
 * @since 2018/1/19
 */

class Slf4jLoggerx extends Slf4jLogger {

    private final org.slf4j.Logger logger;

    public Slf4jLoggerx() {
        this(feign.Logger.class);
    }

    public Slf4jLoggerx(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public Slf4jLoggerx(String name) {
        this(LoggerFactory.getLogger(name));
    }

    Slf4jLoggerx(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logger.isDebugEnabled()) {

            log(configKey, "---> %s %s HTTP/1.1", request.method(), request.url());
            if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

                for (String field : request.headers().keySet()) {
                    for (String value : valuesOrEmpty(request.headers(), field)) {
                        log(configKey, "%s: %s", field, value);
                    }
                }

                int bodyLength = 0;
                if (request.body() != null) {
                    bodyLength = request.body().length;
                    if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                        String bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
                        log(configKey, ""); // CRLF
                        //XXX: thinking
                        if(bodyLength <= 2048){

                            log(configKey, "thinking-request-%s", bodyText != null ? bodyText : "Binary data");
                        }

                    }
                }
                log(configKey, "---> END HTTP (%s-byte body)", bodyLength);
            }

        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
                                              long elapsedTime) throws IOException {
        if (logger.isDebugEnabled()) {
                String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : "";
                int status = response.status();
                log(configKey, "<--- HTTP/1.1 %s%s (%sms)", status, reason, elapsedTime);
                if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

                    for (String field : response.headers().keySet()) {
                        for (String value : valuesOrEmpty(response.headers(), field)) {
                            log(configKey, "%s: %s", field, value);
                        }
                    }

                    int bodyLength = 0;
                    if (response.body() != null && !(status == 204 || status == 205)) {
                        // HTTP 204 No Content "...response MUST NOT include a message-body"
                        // HTTP 205 Reset Content "...response MUST NOT include an entity"
                        if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                            log(configKey, ""); // CRLF
                        }
                        byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                        bodyLength = bodyData.length;

                        //XXX:thinking
                        if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0 && bodyLength<= 2048) {
                            log(configKey, "thinking-response-%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                        }
                        log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                        return response.toBuilder().body(bodyData).build();
                    } else {
                        log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                    }
                }
                return response;

        }

        return response;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format(methodTag(configKey) + format, args));
        }
    }



    static String decodeOrDefault(byte[] data, Charset charset, String defaultValue) {
        if (data == null) {
            return defaultValue;
        }
        Util.checkNotNull(charset, "charset");
        try {
            return charset.newDecoder().decode(ByteBuffer.wrap(data)).toString();
        } catch (CharacterCodingException ex) {
            return defaultValue;
        }
    }


}
