package com.yy.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class HttpHelper {

    private static final Log logger= LogFactory.getLog(HttpHelper.class);

    private static final String UTF8 = "UTF-8";

    @SuppressWarnings("finally")
    public static String httpPost(String Url, NameValuePair[] data) {
        String result = "";
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(Url);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,UTF8);
        postMethod.setRequestBody(data);
        // 执行postMethod
        int statusCode;
        try {
            statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] responseBody = postMethod.getResponseBody();
                result = new String(responseBody,UTF8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
            return result;
            // 释放连接
        }
    }

}


