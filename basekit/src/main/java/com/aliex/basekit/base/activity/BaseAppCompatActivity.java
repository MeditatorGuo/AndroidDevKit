package com.aliex.basekit.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.basekit.base.presenter.BasePresenter;
import com.aliex.commonlib.utils.ActivityManagerUtils;
import com.aliex.commonlib.utils.DrawerToast;
import com.aliex.commonlib.utils.LoggerUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BaseAppCompatActivity<V, T extends BasePresenter<V>> extends RxAppCompatActivity {

    private Activity mActivity;
    private ActivityManagerUtils activityManagerUtils;
    private DrawerToast mDrawerToast;

    protected T mPresenter; // Presenter 对象

    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        mActivity = this;
        activityManagerUtils = ActivityManagerUtils.getInstance();
        activityManagerUtils.pushActivity(this);
        mDrawerToast = DrawerToast.getInstance(getApplicationContext());

    }

    protected abstract int getLayoutId();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LoggerUtils.i("ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LoggerUtils.i("ORIENTATION_PORTRAIT");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        activityManagerUtils.popAnyActivity(this);
    }
}
