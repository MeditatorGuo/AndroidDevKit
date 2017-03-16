package com.aliex.devkit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aliex.devkit.ui.home.ArticleFragment;

import java.util.List;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<ArticleFragment> mFragments;
    private String[] mTitles;

    public static FragmentAdapter newInstance(FragmentManager fm, List<ArticleFragment> fragments, String[] titles) {
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(fm);
        mFragmentAdapter.mFragments = fragments;
        mFragmentAdapter.mTitles = titles;
        return mFragmentAdapter;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
