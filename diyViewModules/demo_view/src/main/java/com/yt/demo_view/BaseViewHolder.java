package com.yt.demo_view;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/28.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    /**
     * 用语缓存已找到的界面
     */
    private SparseArray<View> mView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    /**
     * 获取view
     * @param viewID
     * @param <T>
     * @return
     */
    public <T extends View> T  getView(int viewID){
        View view = mView.get(viewID);
        if(view == null){
            view = itemView.findViewById(viewID);
            mView.put(viewID,view);
        }

        return (T) view;
    }


    /**
     * 设置文本
     */
    public BaseViewHolder setText(int viewId, CharSequence value){
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * 设置点击事件
     * @param listener
     */
    public void setOnItemClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
    }
}
