package com.yt.demo_view;

import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/28.
 */

public class FlexboxAdapter extends CommonAdapter<String> {

    public FlexboxAdapter(ArrayList<String> data, int layoutId) {
        super(data, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_title,s);
        ViewGroup.LayoutParams params = holder.getView(R.id.tv_title).getLayoutParams();
        if(params instanceof FlexboxLayoutManager.LayoutParams){
            ((FlexboxLayoutManager.LayoutParams) params).setFlexGrow(1.0f);
        }
    }
}
