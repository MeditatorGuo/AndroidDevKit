package com.aliex.devkit.ui.user;

import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.model.User;
import com.trello.rxlifecycle.LifecycleTransformer;

import javax.annotation.Nonnull;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public class UserActivity extends BaseAppCompatActivity implements UserContract.View {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void initUser(User user) {

    }

    @Override
    public void takePhoto() {

    }

    @Nonnull
    @Override
    public LifecycleTransformer bindUntilEvent(@Nonnull Object event) {
        return null;
    }
}
