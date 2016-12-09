package com.hjq.library.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hjq.library.exception.OkHttpException;
import com.hjq.library.listener.DisposeDataHandle;
import com.hjq.library.listener.DisposeDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hjq on 2016/11/26.
 */

public class OkHttpResponse implements Callback {

    protected final String RESULT_CODE = "status";
    protected final int RESULT_CODE_DUCCESS = 1;
    protected final String ERROR_MSG = "message";
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener listener;
    private Class<?> mclass;
    private Handler mDelieverHandler;

    public OkHttpResponse(DisposeDataHandle handle) {
        this.listener = handle.listener;
        this.mclass = handle.mclass;
        this.mDelieverHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDelieverHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        mDelieverHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {
        if (TextUtils.isEmpty(result)) {
            listener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject obj = new JSONObject(result);
            if (obj.has(RESULT_CODE)) {
                if (obj.optInt(RESULT_CODE) == RESULT_CODE_DUCCESS) {
                    if (mclass == null) {
                        listener.onSuccess(obj);
                    } else {
                        Object clazzObj = null;
                        try {
                            clazzObj = JSON.parseObject(result, mclass);
                            if (clazzObj == null) {
                                listener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                            } else {
                                // 所有逻辑正常，返回实体对象
                                listener.onSuccess(clazzObj);
                            }
                        } catch (Exception e) {
                            listener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                } else {
                    listener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
                }
            } else {
                listener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            }
        } catch (Exception e) {
            if (e instanceof JSONException) {
                listener.onSuccess(result);
            } else {
                listener.onFailure(new OkHttpException(OTHER_ERROR, EMPTY_MSG));
            }
        }
    }
}
