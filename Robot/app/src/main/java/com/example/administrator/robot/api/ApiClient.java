package com.example.administrator.robot.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2016/3/14.
 */
public class ApiClient {

    private static AsyncHttpClient client = null;

    public static void init(){
        if (client == null){
            client = new AsyncHttpClient();
        }
    }

    public static void getUrl(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        client.get(url,params,responseHandler);
    }

    public static void postUrl(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        client.post(url,params,responseHandler);
    }

}
