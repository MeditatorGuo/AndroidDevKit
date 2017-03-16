package com.aliex.devkit.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.event.EventTags;
import com.apt.annotation.apt.ApiFactory;
import com.apt.annotation.javassist.Bus;

/**
 * author: Aliex <br/>
 * created on: 2017/3/16 <br/>
 * description: <br/>
 */

public class ArticleFragment extends Fragment {
    private TRecyclerView mXRecyclerView;
    private String type;

    public static ArticleFragment newInstance(String type) {
        Bundle arguments = new Bundle();
        arguments.putString(Const.TYPE, type);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getContext());
        mXRecyclerView.setViewType(R.layout.list_item_card_main);
        return mXRecyclerView;
    }

    @Bus(EventTags.ON_RELEASE_OPEN)
    public void onRelease() {
        if (TextUtils.equals(type, Const.OPEN_TYPE))
            mXRecyclerView.getPresenter().fetch();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getString(Const.TYPE);
        mXRecyclerView.getPresenter().setDbRepository(DbFactory::getAllImages)
                .setNetRepository(ApiFactory::getAllImages).setParam(Const.TYPE, type).fetch();
    }
}
