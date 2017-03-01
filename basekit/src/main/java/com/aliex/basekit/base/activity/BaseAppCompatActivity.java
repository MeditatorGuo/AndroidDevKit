package com.aliex.basekit.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.aliex.basekit.base.iview.IBaseView;
import com.aliex.commonlib.utils.ActivityManagerUtils;
import com.aliex.commonlib.utils.DrawerToast;
import com.aliex.commonlib.utils.LoggerUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public class BaseAppCompatActivity extends RxAppCompatActivity implements IBaseView {

    private Activity mActivity;
    private ActivityManagerUtils activityManagerUtils;
    private DrawerToast mDrawerToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        activityManagerUtils = ActivityManagerUtils.getInstance();
        activityManagerUtils.pushActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mDrawerToast = DrawerToast.getInstance(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

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
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManagerUtils.popAnyActivity(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showExceptions(Throwable ex) {

    }
}
