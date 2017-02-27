package com.aliex.basekit.base.presenter;

import android.content.Context;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BasePresenter {

    protected Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    public abstract Throwable doError(Throwable e);

}
