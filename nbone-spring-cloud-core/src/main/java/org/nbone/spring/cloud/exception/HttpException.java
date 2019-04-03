package org.nbone.spring.cloud.exception;

/**
 * Created by chenyicheng on 2018/3/15.
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2018/3/15
 */
public class HttpException extends RuntimeException {

    private String url;

    private int code;

    /**
     * 异常名称
     */
    private  String exceptionName;




    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }



    public HttpException(int code, String message,String url) {
        super(message);
        this.code = code;
        this.url = url;

    }

    public HttpException(int code,String message,String url,String exceptionName) {
        super(message);
        this.code = code;

        this.url = url;
        this.exceptionName = exceptionName;
    }




    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }




}
