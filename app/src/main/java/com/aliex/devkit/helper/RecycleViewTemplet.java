package com.aliex.devkit.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.aliex.devkit.R;
import com.aliex.devkit.adapter.RecycleViewAdapter;

import java.util.List;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class RecycleViewTemplet<M> extends FrameLayout implements AdapterPresenter.IAdapterView {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerview;
    private LinearLayout ll_emptyView;
    private int headType, footType;
    private GridLayoutManager mLayoutManager;
    private AdapterPresenter<M> mAdapterPresenter;
    private RecycleViewAdapter<M> mCommAdapter;
    private boolean isHasHeadView = false, isHasFootView = false, isEmpty = false, isReverse = false;

    public RecycleViewTemplet(Context context) {
        super(context);
        init(context, null);
    }

    public RecycleViewTemplet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RecycleViewTemplet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TRecyclerView);
        headType = array.getResourceId(R.styleable.TRecyclerView_headType, 0);
        footType = array.getResourceId(R.styleable.TRecyclerView_footType, 0);
        int itemType = array.getResourceId(R.styleable.TRecyclerView_itemType, 0);
        isReverse = array.getBoolean(R.styleable.TRecyclerView_isReverse, false);
        boolean isRefreshable = array.getBoolean(R.styleable.TRecyclerView_isRefreshable, true);
        array.recycle();

        View layout = inflate(context, R.layout.layout_list_recyclerview, this);
        swipeRefresh = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
        recyclerview = (RecyclerView) layout.findViewById(R.id.recyclerview);
        ll_emptyView = (LinearLayout) layout.findViewById(R.id.ll_emptyview);
        mAdapterPresenter = new AdapterPresenter<>(this);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(context, 1);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        mCommAdapter = new RecycleViewAdapter<>();
        recyclerview.setAdapter(mCommAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerview.getAdapter() != null && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerview.getAdapter().getItemCount() && mCommAdapter.isHasMore)
                    mAdapterPresenter.fetch();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                super.onScrolled(recyclerView, arg0, arg1);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = recyclerview.getAdapter().getItemViewType(position);
                if (itemViewType == R.layout.list_footer_view) {
                    return 1;
                } else {
                    return 1;
                }
            }
        });
        ll_emptyView.setOnClickListener((view -> {
            isEmpty = false;
            ll_emptyView.setVisibility(View.GONE);
            swipeRefresh.setVisibility(View.VISIBLE);
            reFetch();
        }));

        if (itemType != 0)
            setViewType(itemType);
        swipeRefresh.setEnabled(isRefreshable);
        if (isReverse) {
            mLayoutManager.setStackFromEnd(true);// 列表再底部开始展示，反转后由上面开始展示
            mLayoutManager.setReverseLayout(true);// 列表翻转
            recyclerview.setLayoutManager(mLayoutManager);
        }

    }

    public AdapterPresenter getPresenter() {
        return mAdapterPresenter;
    }

    public RecycleViewTemplet<M> setTypeSelector(RecycleViewAdapter.TypeSelector mTypeSelector) {
        this.mCommAdapter.setTypeSelector(mTypeSelector);
        return this;
    }

    public RecycleViewTemplet<M> setFootData(Object data) {
        isHasFootView = footType != 0;
        this.mCommAdapter.setFooterViewType(footType, data);
        return this;
    }

    public RecycleViewTemplet<M> setHeadData(Object data) {
        isHasHeadView = headType != 0;
        this.mCommAdapter.setHeadViewType(headType, data);
        return this;
    }

    public void setViewType(@LayoutRes int type) {
        this.mCommAdapter.setViewType(type);
    }

    public RecycleViewTemplet<M> setData(List<M> data) {
        resetEmpty();
        mCommAdapter.setBeans(data, 1);
        return this;
    }

    public void reFetch() {
        mAdapterPresenter.setBegin(0);
        swipeRefresh.setRefreshing(true);
        mAdapterPresenter.fetch();
    }

    @Override
    public void setEmpty() {
        if ((!isHasHeadView || isReverse && !isHasFootView) && !isEmpty) {
            isEmpty = true;
            ll_emptyView.setVisibility(View.VISIBLE);
            swipeRefresh.setVisibility(View.GONE);
        }
    }

    @Override
    public void setRemoteData(List data, int begin) {
        swipeRefresh.setRefreshing(false);
        mCommAdapter.setBeans(data, begin);
        if ((begin == 1) && (data == null || data.size() == 0))
            setEmpty();
        else if (isReverse)
            recyclerview.scrollToPosition(mCommAdapter.getItemCount() - data.size() - 2);
    }

    @Override
    public void setLocalData(List data) {
        swipeRefresh.setRefreshing(false);
        mCommAdapter.setBeans(data, -1);
        if ((data == null || data.size() == 0))
            setEmpty();
        else if (isReverse)
            recyclerview.scrollToPosition(mCommAdapter.getItemCount() - data.size() - 2);
    }

    @Override
    public void resetEmpty() {
        if (isEmpty) {
            ll_emptyView.setVisibility(View.GONE);
            swipeRefresh.setVisibility(View.VISIBLE);
        }
    }
}
