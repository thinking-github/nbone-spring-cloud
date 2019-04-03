package org.nbone.spring.cloud.exception;

/**
 * Created by chenyicheng on 2017/9/5.
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2017/9/5
 */
public class HttpIllegalArgumentException extends IllegalArgumentException {

    private int statusCode = -1;
    private Integer code;

    private String url;




    public HttpIllegalArgumentException() {
        super();
    }

    public HttpIllegalArgumentException(String message) {
        super(message);
    }

    public HttpIllegalArgumentException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpIllegalArgumentException(Integer code, String message,int statusCode,String url) {
        super(message);
        this.code = code;

        this.statusCode = statusCode;
        this.url = url;
    }

    public HttpIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpIllegalArgumentException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public HttpIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public HttpIllegalArgumentException(int statusCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
    }





    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
