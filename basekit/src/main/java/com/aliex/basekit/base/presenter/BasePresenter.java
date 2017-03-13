package com.aliex.basekit.base.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BasePresenter<T> {

    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
