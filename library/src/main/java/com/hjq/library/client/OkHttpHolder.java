package com.hjq.library.client;

import android.os.Handler;
import android.os.Looper;
import com.hjq.library.https.HttpsUtils;
import com.hjq.library.listener.DisposeDataHandle;
import com.hjq.library.request.OkHttpRequest;
import com.hjq.library.request.OkHttpRequestParams;
import com.hjq.library.response.OkHttpResponse;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hjq on 2016/11/26.
 * 用来发送get,post请求的工具类,设置一些请求参数
 */

public class OkHttpHolder {
    private static final int TIME_OUT = 30;
    private static OkHttpClient okHttpClient;

    private static Handler mDelieverHandler;

    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager); // 信任所有ssl证书

        okHttpClient = okHttpBuilder.build();
        mDelieverHandler = new Handler(Looper.getMainLooper());
    }

    public static void post(String url, OkHttpRequestParams params, final DisposeDataHandle handler){
        try{
            Request request = OkHttpRequest.createPostRequest(url, params);
            Call call = okHttpClient.newCall(request);
            call.enqueue(new OkHttpResponse(handler));
        }catch (final Exception e){
            mDelieverHandler.post(new Runnable() {
                @Override
                public void run() {
                    handler.listener.onFailure(e);
                }
            });
        }
    }

    public static void get(String url, OkHttpRequestParams params, final DisposeDataHandle handler){
        try {
            Request request = OkHttpRequest.createGetRequest(url, params);
            Call call = okHttpClient.newCall(request);
            call.enqueue(new OkHttpResponse(handler));
        }catch (final Exception e){
            mDelieverHandler.post(new Runnable() {
                @Override
                public void run() {
                    handler.listener.onFailure(e);
                }
            });
        }
    }
}
