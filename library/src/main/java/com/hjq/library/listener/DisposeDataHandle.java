package com.hjq.library.listener;

/**
 * Created by hjq on 2016/11/26.
 * 处理数据响应
 */

public class DisposeDataHandle<T> {
    public DisposeDataListener listener = null;
    public Class<?> mclass;

    public DisposeDataHandle(DisposeDataListener listener){
        this.listener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener,Class<?> mclass){
        this.listener = listener;
        this.mclass = mclass;
    }

}
