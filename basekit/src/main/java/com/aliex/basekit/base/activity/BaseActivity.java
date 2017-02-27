package com.aliex.basekit.base.activity;

import android.support.v7.app.AppCompatActivity;

import com.aliex.basekit.base.iview.IBaseView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description:  <br/>
 */

public class BaseActivity extends RxAppCompatActivity implements IBaseView{



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
