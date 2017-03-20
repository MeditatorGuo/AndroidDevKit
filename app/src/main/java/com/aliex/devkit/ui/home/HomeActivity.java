package com.aliex.devkit.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.activity.BaseAppCompatActivity;
import com.aliex.devkit.adapter.FragmentAdapter;
import com.aliex.devkit.databinding.ActivityMainBinding;
import com.aliex.devkit.model.User;
import com.apt.annotation.apt.Router;

import rx.Observable;

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
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        if (mViewBinding.dlMainDrawer.isDrawerOpen(Gravity.LEFT))
            mViewBinding.dlMainDrawer.closeDrawers();
        else
            super.onBackPressed();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mViewBinding.dlMainDrawer,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mViewBinding.dlMainDrawer.addDrawerListener(mDrawerToggle);
        mViewBinding.nvMainNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            mViewBinding.dlMainDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showTabList(String[] mTabs) {
        Observable.from(mTabs).map(ArticleFragment::newInstance).toList()
                .map(fragments -> FragmentAdapter.newInstance(getSupportFragmentManager(), fragments, mTabs))
                .subscribe(mFragmentAdapter -> mViewBinding.viewpager.setAdapter(mFragmentAdapter));

        mViewBinding.tabs.setupWithViewPager(mViewBinding.viewpager);

    }

}
