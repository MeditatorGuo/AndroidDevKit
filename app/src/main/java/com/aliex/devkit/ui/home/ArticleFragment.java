package com.aliex.devkit.ui.home;

import com.aliex.aptlib.ApiFactory;
import com.aliex.devkit.Const;
import com.aliex.devkit.R;
import com.aliex.devkit.data.LocalFactory;
import com.aliex.devkit.helper.RecycleViewTemplet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * author: Aliex <br/>
 * created on: 2017/3/16 <br/>
 * description: <br/>
 */

public class ArticleFragment extends Fragment {
    private RecycleViewTemplet recycleViewTemplet;
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
        recycleViewTemplet = new RecycleViewTemplet(getContext());
        recycleViewTemplet.setViewType(R.layout.list_item_card_main);
        return recycleViewTemplet;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getString(Const.TYPE);
        recycleViewTemplet.getPresenter().setLocalRepository(LocalFactory::getAllImages)
                .setRemoteRepository(ApiFactory::getAllImages).setParams(Const.TYPE, type).fetch();
    }
}
