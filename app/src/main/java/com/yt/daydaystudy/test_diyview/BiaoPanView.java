package com.yt.daydaystudy.test_diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 表盘自定义view
 * Created by ${zhangyuanchao} on 2018/1/10.
 */

public class BiaoPanView extends View {

    private Paint circlePaint;
    private Paint textPaint;
    private Paint keduPaint;

    public BiaoPanView(Context context) {
        this(context, null);
    }

    public BiaoPanView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BiaoPanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.STROKE);
        keduPaint = new Paint();
        keduPaint.setColor(Color.BLUE);
        keduPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(0,0,10,circlePaint);
    }
}
