package com.yt.demo_view;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/28.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    /**
     * 用语缓存已找到的界面
     */
    private SparseArray<View> mView;

    public ViewHolder(View itemView) {
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
     * 通用功能进行封装
     */
    public ViewHolder setText(int viewId,String text){
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }


}
