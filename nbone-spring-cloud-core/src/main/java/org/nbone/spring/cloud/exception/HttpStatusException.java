package org.nbone.spring.cloud.exception;

/**
 * Created by chenyicheng on 2018/3/15.
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2018/3/15
 */
public class HttpStatusException  extends HttpException {

    private static final long serialVersionUID = -6108401218506461805L;


    /**
     * http 错误状态码 默认 -1 没有设置
     */
    private int statusCode = -1;



    public static HttpStatusException create(String msg) {
        return new HttpStatusException(msg);
    }

    public static HttpStatusException create(int code, String msg) {
        return new HttpStatusException(code, msg);
    }




    public HttpStatusException(String msg) {
        super(msg);
    }

    public HttpStatusException(int code, String msg) {
        super(code,msg);
    }

    public HttpStatusException(String message, Throwable cause) {
        super(message, cause);
    }



    public HttpStatusException(int code, String message,int statusCode, String url) {
        super(code,message,url);
        this.statusCode = statusCode;

    }

    public HttpStatusException(int code,String message,int statusCode, String url,String exceptionName) {
        super(code,message,url,exceptionName);
        this.statusCode = statusCode;
    }





    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


}
