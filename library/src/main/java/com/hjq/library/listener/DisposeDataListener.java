package com.hjq.library.listener;

/**
 * Created by hjq on 2016/11/26.
 * 数据响应接口
 */

public interface DisposeDataListener {

    public void onSuccess(Object responseObj);

    public void onFailure(Object responseObj);
}
