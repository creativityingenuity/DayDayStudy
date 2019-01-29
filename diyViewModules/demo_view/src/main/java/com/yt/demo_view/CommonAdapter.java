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

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected ArrayList<T> mData;
    protected int mLayoutId;
    /**
     * 多布局
     */
    private MultipleType<T> mMultipleType;

    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public CommonAdapter(ArrayList<T> data, int layoutId) {
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    /**
     * 多布局使用的构造
     */
    public CommonAdapter(ArrayList<T> data, int layoutId, MultipleType multipleType) {
        this.mData = data;
        this.mLayoutId = layoutId;
        this.mMultipleType = multipleType;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultipleType != null) {
            mLayoutId = viewType;
        }
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
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
     * @param position
     * @return 根据position位置设置不同视图类型
     */
    @Override
    public int getItemViewType(int position) {
        if (mData != null && mData.size() > 0&&mMultipleType!=null) {
            return mMultipleType.getLayoutId(mData.get(position), position);
        }
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * 多布局条目类型
     */
    interface MultipleType<V> {
        int getLayoutId(V item, int position);
    }

    /**
     * 进行recyclerview数据设置
     *
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void bindData(BaseViewHolder holder, T t, int position);
}
