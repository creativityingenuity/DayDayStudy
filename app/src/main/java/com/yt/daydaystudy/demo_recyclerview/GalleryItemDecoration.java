package com.yt.daydaystudy.demo_recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import yt.myutils.core.ConvertUtils;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/5.
 * 设置分割线以及item之间的间隔
 */

public class GalleryItemDecoration extends RecyclerView.ItemDecoration {
    //自定义默认item边距
    int mPagerMargin = 10;
    //第一张图片的左边距
    int mLeftPageVisibleWidth = 125;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获得当前item的postion
        int postion = parent.getChildAdapterPosition(parent);
        //获得item的数量
        int itemCount = parent.getAdapter().getItemCount();
        //设置队列的左右边距
        int leftMargin = postion == 0 ? ConvertUtils.dp2px(mLeftPageVisibleWidth) : ConvertUtils.dp2px(mPagerMargin);
        int rightMargin = postion == itemCount - 1 ? ConvertUtils.dp2px(mLeftPageVisibleWidth) : ConvertUtils.dp2px(mPagerMargin);
        //设置params
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        params.setMargins(leftMargin,30,rightMargin,60);
        view.setLayoutParams(params);
        super.getItemOffsets(outRect, view, parent, state);
    }
}
