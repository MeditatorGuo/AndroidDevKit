package com.aliex.commonlib.net.callback;

import com.aliex.commonlib.net.exception.ApiException;

/**
 * @Description: API操作回调
 */
public abstract class ApiCallback<T> {
    public abstract void onStart();

    public abstract void onError(ApiException e);

    public abstract void onCompleted();

    public abstract void onNext(T t);
}
