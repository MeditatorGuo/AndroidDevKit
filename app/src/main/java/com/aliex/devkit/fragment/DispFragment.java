package com.aliex.devkit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliex.basekit.base.fragment.BaseFragment;
import com.aliex.commonlib.utils.ToastUtils;
import com.aliex.devkit.R;
import com.aliex.devkit.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

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
    SwipeRefreshLayout swipeRefreshLayout;
    private static List<String> mList;

    private RecyclerViewAdapter recyclerViewAdapter;

    static {
        mList = new ArrayList<String>();
        mList.add("aaaaaaaaaaaaaaaaaaaaaaaa");
        mList.add("bbbbbbbbbbbbbbbbbbbbbbbb");
        mList.add("cccccccccccccccccccccccc");
        mList.add("dddddddddddddddddddddddd");
        mList.add("eeeeeeeeeeeeeeeeeeeeeeee");
        mList.add("hhhhhhhhhhhhhhhhhhhhhhhh");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mList);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("TAG", "ASDFGHJKL");
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(recyclerViewAdapter);

    }
}
