package com.aliex.devkit.presenter;

import android.content.Context;

import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.devkit.iview.IMainView;

/**
 * author: Aliex <br/>
 * created on: 2017/3/8 <br/>
 * description: <br/>
 */

public class MainPresenter extends BasePresenter<IMainView> {
    public MainPresenter(Context context) {
        super(context);
    }

    @Override
    public Throwable doError(Throwable e) {
        return null;
    }
}
