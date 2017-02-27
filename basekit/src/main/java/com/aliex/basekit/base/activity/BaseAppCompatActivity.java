package com.aliex.basekit.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aliex.basekit.base.iview.IBaseView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BaseAppCompatActivity extends RxAppCompatActivity implements IBaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        initToolbar(savedInstanceState);
        initViews(savedInstanceState);
        initData();
        initListeners();
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
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected <V extends View> V findView(int id) {

        return (V) this.findViewById(id);
    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void initListeners();

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
