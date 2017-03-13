package com.aliex.devkit.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;

import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.databinding.ActivityMainBinding;
import com.aliex.devkit.model.User;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */

public class HomeActivity extends BaseAppCompatActivity<HomePresenter, ActivityMainBinding>
        implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void showTabList(String[] mTabs) {

    }

    @Override
    public void initUserInfo(User user) {

    }

    @Override
    public void onOpenRelease() {

    }

}
