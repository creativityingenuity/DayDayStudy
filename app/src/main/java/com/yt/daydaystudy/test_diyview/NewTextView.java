package com.yt.daydaystudy.test_diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/3/4.
 */

public class NewTextView extends TextView {

    private Paint paint1, paint2;

    public NewTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //实例化画笔1
        paint1 = new Paint();
        //设置颜色
        paint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        //设置style
        paint1.setStyle(Paint.Style.FILL);

        //同上
        paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制外层
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint1);
        //绘制内层
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, paint2);

        canvas.save();
        //绘制文字前平移10像素
        canvas.translate(10, 0);
        //父类完成方法
        super.onDraw(canvas);
        canvas.restore();
    }
}
