package com.aliex.devkit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliex.basekit.base.fragment.BaseFragment;
import com.aliex.devkit.R;
import com.aliex.devkit.adapter.RecyclerViewAdapter;
import com.aliex.uilib.widget.MultiSwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Aliex <br/>
 * created on: 2017/3/7 <br/>
 * description: <br/>
 */

public class DispFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout mMultiSwipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.list_fragment;
    }

    private void initMultiSwipeRefreshLayout() {
        if (this.mMultiSwipeRefreshLayout != null) {
            this.mMultiSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        }

        if (this.mMultiSwipeRefreshLayout != null) {
            this.mMultiSwipeRefreshLayout.setOnRefreshListener(() -> onSwipeRefresh());
        }
    }

    public void onSwipeRefresh() {
        return;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        initMultiSwipeRefreshLayout();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
    }
}
