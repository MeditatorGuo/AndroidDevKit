package com.aliex.devkit.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aliex.devkit.BR;
import com.aliex.devkit.Const;
import com.aliex.devkit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class RecycleViewAdapter<M> extends RecyclerView.Adapter<RecycleViewHolder> {

    private TypeSelector<M> mTypeSelector;
    private List<M> mItemList = new ArrayList<>();
    public boolean isHasMore = true;
    private int viewType, isHasFooter = 1, isHasHeader = 0, mHeadViewType, mFooterViewType = R.layout.list_footer_view;
    private Object mHeadData, mFootData;

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecycleViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.mViewDataBinding.setVariable(BR.item, getItem(position));
        holder.mViewDataBinding.executePendingBindings();

    }

    public Object getItem(int position) {
        return isHasFooter == 1 && position + 1 == getItemCount() ? (mFootData == null ? isHasMore : mFootData)
                : isHasHeader == 1 && position == 0 ? mHeadData : mItemList.get(position - isHasHeader);
    }

    @Override
    public int getItemCount() {
        return mItemList.size() + isHasFooter + isHasHeader;
    }

    public void setViewType(@LayoutRes int type) {
        this.viewType = type;
    }

    public void setTypeSelector(TypeSelector mTypeSelector) {
        this.mTypeSelector = mTypeSelector;
        this.viewType = Const.FLAG_MULTI_VH;
    }

    public void setHeadViewType(@LayoutRes int i, Object data) {
        this.isHasHeader = i == 0 ? 0 : 1;
        if (isHasHeader == 1) {
            this.isHasHeader = 1;
            this.mHeadViewType = i;
            this.mHeadData = data;
        }
    }

    public void setFooterViewType(@LayoutRes int i, Object data) {
        this.isHasFooter = i == 0 ? 0 : 1;
        if (isHasFooter == 1) {
            this.mFootData = data;
            this.isHasFooter = 1;
            this.mFooterViewType = i;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isHasHeader == 1 && position == 0 ? mHeadViewType
                : (isHasFooter == 1 && position + 1 == getItemCount() ? mFooterViewType
                        : viewType == Const.FLAG_MULTI_VH ? mTypeSelector.getType((M) getItem(position)) : viewType);
    }

    public void setBeans(List<M> data, int begin) {
        if (data == null)
            data = new ArrayList<>();
        this.isHasMore = data.size() >= Const.PAGE_COUNT && (data.size() > 0 && begin > 0);
        if (begin > 1)
            this.mItemList.addAll(data);
        else
            this.mItemList = data;
        notifyDataSetChanged();
    }

    public interface TypeSelector<M> {
        int getType(M m);
    }
}
