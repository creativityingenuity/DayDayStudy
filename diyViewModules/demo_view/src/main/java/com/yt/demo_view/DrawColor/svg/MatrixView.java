package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.yt.demo_view.R;


/**
 * 自定义 地图 控件
 * Created by user on 2016/3/3.
 */
public class MatrixView extends RelativeLayout {

    private Context context;
    // 显示 地图 底图和线 的 控件
    private SVGPathView mSVGPathView;

    // 首次 放大缩小的 倍数
    private float firstScale;


    public MatrixView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    // 初始化
    private void init() {
        LayoutInflater.from(context).inflate(R.layout.view_mymap, this);
        mSVGPathView = this.findViewById(R.id.imageView);
        // 设置 触摸 监听
        this.setOnTouchListener(new MyTouchListener());
        mSVGPathView.setImageResource(R.mipmap.ijshdjsh);
        showMap(1f);
    }


    /**
     * 根据 传入 的 缩放 比例 显示 地图
     *
     * @param scale 地图缩放 比例
     */
    private void showMap(float scale) {
        this.firstScale = scale;
        Matrix matrix = new Matrix();
        // 放大缩小 适应屏幕宽度
        matrix.preScale(this.firstScale, this.firstScale);
        mSVGPathView.setImageMatrix(matrix);
    }


    /**
     * 地图 移动 放大 监听
     */
    private class MyTouchListener implements OnTouchListener {
        /**
         * 记录是拖拉照片模式还是放大缩小照片模式
         */
        private int mode = 0;// 初始状态
        /**
         * 拖拉照片模式
         */
        private static final int MODE_DRAG = 1;
        /**
         * 放大缩小照片模式
         */
        private static final int MODE_ZOOM = 2;

        /**
         * 用于记录开始时候的坐标位置
         */
        private PointF startPoint = new PointF();
        /**
         * 用于记录拖拉图片移动的坐标位置
         */
        private Matrix matrix = new Matrix();
        /**
         * 用于记录图片要进行拖拉时候的坐标位置
         */
        private Matrix currentMatrix = new Matrix();

        /**
         * 两个手指的开始距离
         */
        private float startDis;
        /**
         * 两个手指的中间点
         */
        private PointF midPoint;


        /**
         * 检验scale，使图像缩放后不会超出最大倍数
         *
         * @param scale
         * @param values
         * @return
         */
        private float checkMaxScale(float scale, float[] values) {
            Log.i("myBaseMapAndLines", "MSCALE_X:" + scale * values[Matrix.MSCALE_X] + ",scale:" + scale);
            if (scale * values[Matrix.MSCALE_X] < mMinScale) {
                scale = mMinScale / values[Matrix.MSCALE_X];
                Log.i("myBaseMapAndLines2", "MSCALE_X:" + scale * values[Matrix.MSCALE_X] + ",scale:" + scale);
            } else if (scale * values[Matrix.MSCALE_X] > mMaxScale) {
                scale = mMaxScale / values[Matrix.MSCALE_X];
                Log.i("myBaseMapAndLines2", "MSCALE_X:" + scale * values[Matrix.MSCALE_X] + ",scale:" + scale);
            }
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
            return scale;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(mSVGPathView.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());

//                Log.i("test", "=onTouch=x==" + event.getX() + "===y==" + event.getY());
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        Log.i("testpan", "dx:" + dx + ",dy:" + dy);
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }

                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
//                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);


                            float[] values = new float[9];
                            currentMatrix.getValues(values);
                            checkMaxScale(scale, values);
                        }
                    }

                    break;
                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    //如果 按下 抬起 时间 大于 2s 则是 长按 事件
//                    longPressTag = System.currentTimeMillis() - downTime > 2000 ? true : false;
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                    if (startPoint.x == event.getX() && startPoint.y == event.getY()) {
                        Matrix imageMatrix = mSVGPathView.getImageMatrix();
                        float[] matrixValues = new float[9];
                        imageMatrix.getValues(matrixValues);

                        boolean b = mSVGPathView.handlerTouch((event.getX() - matrixValues[2]) / matrixValues[0], (event.getY() - matrixValues[5]) / matrixValues[4]);

                        Log.i(b + ",testss,x" + event.getX() + ",y:" + event.getY(), "mScale:" + getScale() + ",left:" + matrixValues[2] + ",top" + matrixValues[5]);
                    }
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(mSVGPathView.getImageMatrix());
                    }
                    break;
            }


            /**
             * 如果 此次 触摸事件  是  移动，放大事件
             * 则 改变 地图 和 坐标点的位置
             */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_MOVE:
                    // 移动 地图
                    mSVGPathView.setImageMatrix(matrix);

                    // 地图原点移动
//                    mSVGPathView.setX((float) (mSVGPathView.getX() * matrixValues[0] + matrixValues[2]));
//                    mSVGPathView.setY((float) (mSVGPathView.getY() * matrixValues[4] + matrixValues[5]));


//                    firstScale = matrixValues[0];


                    /**
                     *如果 外层为ScrollView 此句代码是解决
                     * 地图的移动 和 ScrollView 的滚动冲突的
                     * 当触摸事件在地图范围内时，ScrollView 滚动事件无法响应
                     * 当触摸事件在 地图范围外时，ScrollView可以滚动
                     */
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
            }
            return true;
        }

        /**
         * 最小缩放级别
         */
        float mMinScale = 0.3F;

        /**
         * 最大缩放级别
         */
        float mMaxScale = 30F;

        /**
         * 计算两个手指间的距离
         */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * 计算两个手指间的中间点
         */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }
    }

    //获取当前图片的缩放比例
    public float getScale() {
        float[] values = new float[9];
        mSVGPathView.getImageMatrix().getValues(values);
        return values[Matrix.MSCALE_X];
    }
}
