package com.aliex.devkit.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.databinding.ActivityMainBinding;
import com.aliex.devkit.model.User;
import com.apt.annotation.apt.Router;

/**
 * author: Aliex <br/>
 * created on: 2017/3/13 <br/>
 * description: <br/>
 */
@Router(Const.HOME)
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
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mViewBinding.dlMainDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mViewBinding.dlMainDrawer.addDrawerListener(mDrawerToggle);
        mViewBinding.nvMainNavigation.setNavigationItemSelectedListener(this);
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
