
package org.nbone.spring.cloud.netflix.feign;

import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.nbone.core.exception.ExceptionInfo;
import org.nbone.spring.cloud.exception.HttpException;
import org.nbone.spring.cloud.exception.HttpIllegalArgumentException;
import org.nbone.spring.cloud.exception.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2017/5/12.
 */

@Configuration
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder delegate = new Default();
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        try {

            String body = getStringFromInput(response);
            String url = response.request().url();

           //InputStream inputStream =  response.body().asInputStream();

           ExceptionInfo exceptionInfo = JSON.parseObject(body, ExceptionInfo.class);

            logger.error("methodKey: " + methodKey + " exceptionInfo: " + body);

            if(exceptionInfo == null){
                return new RuntimeException(body);
            }

            String exceptionName = exceptionInfo.getExceptionName();
            int statusCode       = exceptionInfo.getStatusCode();
            int code         = exceptionInfo.getCode();
            String message       = exceptionInfo.getMessage();


            if (HttpIllegalArgumentException.class.getSimpleName().equals(exceptionName)) {
                return new HttpIllegalArgumentException(code, message,statusCode,url);
            }



            if(statusCode == 0 || statusCode == -1){

                return new HttpException(code, message,url,exceptionName);
            }




            return new HttpStatusException(code, message,statusCode,url,exceptionName);

        } catch (IOException e) {
            throw new RuntimeException("read response body faild .thinking");
        }
    }




    public static String getStringFromInput(Response response) throws IOException {
        return IOUtils.toString(response.body().asInputStream(), "utf-8");
    }
}
