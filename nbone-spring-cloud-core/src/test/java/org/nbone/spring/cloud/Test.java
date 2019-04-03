package org.nbone.spring.cloud;

import com.alibaba.fastjson.JSON;
import org.nbone.core.exception.ExceptionInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;

/**
 * Created by chenyicheng on 2018/3/15.
 *
 * @author chenyicheng
 * @version 1.0
 * @since 2018/3/15
 */
public class Test {


    public static void main(String[] args) {


        String json ="{\"code\":-1,\"message\":\"packageNo[ZNCN8m]不能重复\",\"exceptionName\":\"MyIllegalStateException\",\"statusCode\":400}";


        String ss = new String(json);

        StringReader sr = new StringReader(json);





        ExceptionInfo exceptionInfo =   JSON.parseObject(ss.getBytes(Charset.forName("utf-8")),ExceptionInfo.class);
        ExceptionInfo exceptionInfo1 =  JSON.parseObject(json, ExceptionInfo.class);




        System.out.println("");

    }


}
