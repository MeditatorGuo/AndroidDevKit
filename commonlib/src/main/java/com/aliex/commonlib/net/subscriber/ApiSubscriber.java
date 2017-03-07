package com.aliex.commonlib.net.subscriber;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.aliex.commonlib.assist.Network;
import com.aliex.commonlib.net.exception.ApiException;
import com.aliex.commonlib.net.mode.ApiCode;

import java.lang.ref.WeakReference;

import rx.Subscriber;

/**
 * author: Aliex <br/>
 * created on: 2017/3/6 <br/>
 * description: <br/>
 */

public abstract class ApiSubscriber<T> extends Subscriber<T> {

    public WeakReference<Context> contextWeakReference;

    public ApiSubscriber(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!Network.isConnected(contextWeakReference.get())) {
            onError(new ApiException(new NetworkErrorException(), ApiCode.Request.NETWORK_ERROR));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    public abstract void onError(ApiException ex);
}
