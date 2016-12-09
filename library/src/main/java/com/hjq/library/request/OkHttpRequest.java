package com.hjq.library.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by hjq on 2016/11/26.
 * 封装各种类型的请求对象
 */

public class OkHttpRequest {

    /**
     * get请求
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, OkHttpRequestParams params){
        StringBuilder urlBuilder = new StringBuilder();
        if(params != null){
            for(Map.Entry<String, String> entry : params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        int end = urlBuilder.length() - 1;
        String fullUrl = url + urlBuilder.substring(0, (end > 0 ? end : 0)).toString();
        return new Request.Builder()
                .url(fullUrl)
                .get()
                .build();
    }

    /**
     * post请求
     * @param url
     * @param params
     * @return
     */

    public static Request createPostRequest(String url, OkHttpRequestParams params){
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null){
            for(Map.Entry<String, String> entry : params.urlParams.entrySet()){
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = builder.build();
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }


    /**
     * 上传文件
     */
    public static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public static Request createMultiPostRequest(String url, OkHttpRequestParams params){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if(params != null){
            for(Map.Entry<String, Object> entry : params.fileParams.entrySet()){
                if(entry.getValue() instanceof File){
                    builder.addPart(MultipartBody.Part.createFormData(entry.getKey(),
                            null, RequestBody.create(FILE_TYPE, (File)entry.getValue())));
                }else{
                    builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }

    /**
     *
     */

}
