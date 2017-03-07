package com.aliex.commonlib.net.subscriber;

import android.content.Context;

import com.aliex.commonlib.net.callback.ApiCallback;
import com.aliex.commonlib.net.exception.ApiException;

/**
 * author: Aliex <br/>
 * created on: 2017/3/6 <br/>
 * description: <br/>
 */

public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {

    protected ApiCallback<T> callBack;

    public ApiCallbackSubscriber(Context context, ApiCallback<T> callBack) {
        super(context);
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        callBack.onStart();
    }

    @Override
    public void onError(ApiException ex) {
        callBack.onError(ex);
    }

    @Override
    public void onCompleted() {
        callBack.onCompleted();
    }

    @Override
    public void onNext(T t) {
        callBack.onNext(t);
    }
}
