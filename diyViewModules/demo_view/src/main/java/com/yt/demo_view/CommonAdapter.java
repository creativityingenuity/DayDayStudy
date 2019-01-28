package com.yt.demo_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/28.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected ArrayList<T> mData;
    protected int mLayoutId;
    private MultipleType<T> mMultipleType
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public CommonAdapter(ArrayList<T> mData, int mLayoutId) {
        this.mData = mData;
        this.mLayoutId = mLayoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //绑定数据
        bindData(holder, mData.get(position), position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mData.get(position), position);
                }
            });
        }
        //长按点击事件
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onItemLongClick(mData.get(position), position);
                    return false;
                }
            });
        }
    }

    /**
     * 多布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    /**
     * 点击回调
     */
    interface OnItemClickListener {

        void onItemClick(Object data, int position);
    }

    interface OnItemLongClickListener {

        void onItemLongClick(Object data, int position);

    }

    /**
     * 多布局条目类型
     */
    interface MultipleType<T> {
        int getLayoutId(T item, int position);
    }

    /**
     * 进行recyclerview数据设置
     *
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void bindData(ViewHolder holder, T t, int position);
}
