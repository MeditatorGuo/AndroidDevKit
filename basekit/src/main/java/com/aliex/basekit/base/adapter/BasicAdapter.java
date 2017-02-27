package com.aliex.basekit.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aliex.basekit.base.adapter.helper.HelperViewHolder;

import java.util.List;

/**
 * author: Aliex <br/>
 * created on: 2017/2/27 <br/>
 * description: <br/>
 */

public abstract class BasicAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mList;
    protected int[] mLayoutIds;
    protected BaseViewHolder mViewHolder = new HelperViewHolder();

    /**
     * @param data
     *            数据源
     * @param context
     *            上下文
     * @param layoutIds
     *            布局Id
     */
    public BasicAdapter(Context context, List<T> data, int... layoutIds) {
        this.mContext = context;
        this.mList = data;
        this.mLayoutIds = layoutIds;
        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 在初始化的时候不能确定layoutId,才可以不提供,但是必须重checkLayoutId方法
     *
     * @param context
     * @param data
     */
    public BasicAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mList = data;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public BasicAdapter(Context context, int... layoutIds) {
        this.mContext = context;
        this.mLayoutIds = layoutIds;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public BasicAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList != null ? mList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getViewCheckLayoutId(position);
        mViewHolder = mViewHolder.get(mContext, position, convertView, parent, layoutId);
        convert(mViewHolder, position, mList.get(position));
        return mViewHolder.getConvertView(layoutId);
    }

    protected int getViewCheckLayoutId(int position) {
        int layoutId;
        if (mLayoutIds == null) {
            layoutId = checkLayoutId(position, mList.get(position));
        } else {
            if (mLayoutIds.length == 0) {
                throw new ArrayIndexOutOfBoundsException("not layoutId");
            }
            layoutId = mLayoutIds[checkLayout(position, mList.get(position))];
        }
        return layoutId;
    }

    /**
     * <p>
     * 实现具体控件的获取和赋值等业务
     * </p>
     *
     * @param viewHolder
     *            viewHolder
     * @param position
     *            position
     * @param t
     *            数据源中,当前对应的bean
     */
    public abstract <E extends BaseViewHolder> void convert(E viewHolder, int position, T t);

    /**
     * <p>
     * 根据业务逻辑确定layoutId位置,使用在listView中有几种样式
     * </p>
     *
     * @param position
     *            所在位置
     * @param item
     *            对应数据
     * @return 默认使用第一个, 返回下标, 从0开始
     */
    public int checkLayout(int position, T item) {
        return 0;
    }

    /**
     * <p>
     * 根据业务逻辑确定layoutId,只会在构造方法没有传入layoutId时生效
     * </p>
     *
     * @param position
     *            所在位置
     * @param item
     *            对应数据
     * @return 默认为0, 返回Id
     */
    public int checkLayoutId(int position, T item) {
        return 0;
    }

}
