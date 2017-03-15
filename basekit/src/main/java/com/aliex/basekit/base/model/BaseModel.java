package com.aliex.basekit.base.model;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public class BaseModel {
    protected Context mContext;

    public BaseModel(Context context) {
        this.mContext = context;
    }

    protected LifecycleProvider getActivityLifecycleProvider() {

        LifecycleProvider provider = null;
        if (mContext != null && mContext instanceof LifecycleProvider) {
            provider = (LifecycleProvider) mContext;
        }
        return provider;
    }

    public Observable subscribe(Observable mObservable, Subscriber subscriber) {
        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(getActivityLifecycleProvider().bindUntilEvent(ActivityEvent.DESTROY)).subscribe(subscriber);
        return mObservable;
    }

}
