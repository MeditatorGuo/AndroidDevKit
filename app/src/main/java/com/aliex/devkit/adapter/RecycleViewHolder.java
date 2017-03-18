package com.aliex.devkit.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class RecycleViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final T mViewDataBinding;

    public RecycleViewHolder(T t) {
        super(t.getRoot());
        mViewDataBinding = t;
    }
}
