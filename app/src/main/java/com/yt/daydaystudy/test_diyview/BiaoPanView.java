package com.yt.daydaystudy.test_diyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private Path textPath;
    private Path keduPath;

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
        circlePaint.setDither(true);

        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(15);


        postInvalidateDelayed(100);
        keduPaint = new Paint();
        keduPaint.setColor(Color.BLUE);
        keduPaint.setStyle(Paint.Style.STROKE);

        textPath = new Path();
        getScaleY();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布坐标到150 150
        canvas.translate(150,150);
        //画半径为100的圆
        canvas.drawCircle(0,0,100,circlePaint);
        //沿着某条Path来绘制这些文字
        textPath.moveTo(-100,0);
        textPath.lineTo(0,-100);
        canvas.drawTextOnPath("灵魂只能独行",textPath,20,0,textPaint);

        canvas.save();
        for (int i = 1;i<=12;i++) {
            canvas.rotate(30);
            canvas.drawLine(0,-100,0,-80,keduPaint);
        }
        canvas.restore();
    }

}
