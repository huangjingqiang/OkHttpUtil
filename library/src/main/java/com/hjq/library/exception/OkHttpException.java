package com.hjq.library.exception;

/**
 * Created by hjq on 2016/11/26.
 * 异常封装，返回服务器错误码，message
 */

public class OkHttpException extends Exception {
    private int Ecode;
    private Object message;

    public OkHttpException(int Ecode,Object message){
        this.Ecode = Ecode;
        this.message = message;
    }
}
