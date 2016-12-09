package com.hjq.library.request;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hjq on 2016/11/26.
 */

public class OkHttpRequestParams {

    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();

    // TODO 构造函数
    public OkHttpRequestParams(Map<String, Object> source) {
        if(source != null) {
            for (Map.Entry<String, Object> entry : source.entrySet()) {
                if(entry.getValue() instanceof File){
                    put(entry.getKey(), entry.getValue());
                }else{
                    put(entry.getKey(), (String) entry.getValue());
                }
            }
        }
    }

    private void put(String name, String value){
        if(name == null) return;
        urlParams.put(name, value);
    }
    private void put(String name, Object value){
        fileParams.put(name, value);
    }
}
