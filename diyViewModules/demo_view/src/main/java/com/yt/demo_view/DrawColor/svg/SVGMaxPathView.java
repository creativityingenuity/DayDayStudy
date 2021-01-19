package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.yt.demo_view.R;

import java.util.ArrayList;
import java.util.List;

/***
 // * 1.根据SVG的信息实现绘制
 // * 2.根据SVG的颜色信息，实现点击上色
 // * 3.对上色的步骤进行统计
 // * 4.在画一个自定义View，然后根据统计的步骤,自动画到画布上
 // */
public class SVGMaxPathView extends View {

    private static final String TAG = "SVGPathView";


    private GestureDetector mGestureDetector = null;
    private ScaleGestureDetector mScaleGestureDetector = null;

    private final float[] matrixValues = new float[9];// 用于存放矩阵的9个值
    private float InitSclale;//初始化缩放比例（最小缩放比例）

    private Paint paint;

    private Matrix mScaleMatrix;

    private int miniWidth;
    private int miniHeight;
    private int bottomPadding;
    private float scale = 1;
    private RectF mapSize;

    // 存放路径的list
    private List<ProvinceItem> itemList;

    // 存放路径的list
    private List<ProvinceItem> selectedItemList = new ArrayList<>();
    // 存放svg信息的对象
    private ProvinceItem selectedItem;
    // 手势操作
    private GestureDetectorCompat gestureDetectorCompat;

    public SVGMaxPathView(Context context) {
        super(context);
        init(null, 0);
    }

    public SVGMaxPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SVGMaxPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        mScaleMatrix = new Matrix();
        paint = new Paint();
        paint.setAntiAlias(true);
        miniWidth = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_width);
        miniHeight = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_height);

        mGestureDetector = new GestureDetector(getContext(), new Gesturelistener());
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureListener());
        initMove();
        if (!isInEditMode()) {
            //获取地图svg封装信息
            SVGManager.getInstance(getContext()).getProvincePathListAsync((provincePathList, size) -> {

                List<ProvinceItem> list = new ArrayList<>();
                for (SVGPath provincePath : provincePathList) {
                    ProvinceItem item = new ProvinceItem();
                    item.setPath(provincePath.getPath());
                    item.setId(provincePath.getId());
                    item.setPathD(provincePath.getPathD());
                    // color
                    item.setStyleColor(provincePath.getColor());
                    list.add(item);
                }

                mapSize = size;
                itemList = list;

                //刷新布局
                requestLayout();
                postInvalidate();
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        miniWidth = getMeasuredWidth();
        miniHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final List<ProvinceItem> list = itemList;
        if (list != null) {

            int width = getWidth();
            int height = getHeight();
            canvas.save();
            canvas.scale(scale, scale);
            for (ProvinceItem item : list) {
                if (!item.equals(selectedItem)) {
                    item.drawItem(canvas, paint, false);
                }
            }
            if (selectedItemList != null) {
                for (int i = 0; i < selectedItemList.size(); i++) {
                    selectedItemList.get(i).drawItem(canvas, paint, true);
                }
            }
            canvas.restore();
            if (selectedItemList != null) {

                paint.setTypeface(Typeface.DEFAULT);
                paint.setColor(0xFF333333);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.clearShadowLayer();
                paint.setTextSize(14);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 处理手势触摸
     *
     * @param x 当前x
     * @param y 当前y
     * @return 是否触摸到区域部分
     */
    private boolean handlerTouch(int x, int y) {
        ProvinceItem provinceItem = null;
        final List<ProvinceItem> list = itemList;
        if (list == null) {
            return false;
        }
        for (ProvinceItem temp : list) {
            if (temp.isTouched((int) (x / scale), (int) (y / scale))) {
                provinceItem = temp;
                break;
            }
        }

        if (provinceItem != null && !provinceItem.equals(selectedItem)) {
            selectedItem = provinceItem;
            if (!selectedItemList.contains(selectedItem)) {
                selectedItemList.add(selectedItem);
            }
            postInvalidate();
        }
        return provinceItem != null;
    }


    // 把当前选中的东西view都返回给上级
    public List<ProvinceItem> getSelectedItemList() {
        if (selectedItemList.size() > 0) {
            return selectedItemList;
        }
        return null;
    }


    // 获得当前的缩放比例
    private final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    private void initMove() {
        float dx = 0;
        float dy = 0;
//        switch (Grivity) {
//            case 0:
//                dx = (mWidth - right * scale - left * scale) / 2;
//                dy = (mHeigh - bottom * scale - top * scale) / 2;
//                break;
//        }
        mScaleMatrix.postTranslate(dx, dy);
        areaTranslate(dx, dy);
    }

    private class Gesturelistener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            handlerTouch((int) x, (int) y);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
//            AreaOnClick(motionEvent);
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            handlerTouch((int) x, (int) y);
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float dx, float dy) {
            mScaleMatrix.postTranslate(dx, dy);
            areaTranslate(-dx, -dy);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

    private class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            scale = getScale();
            if ((scale < 3 && scaleFactor > 1.0f)
                    || (scale > InitSclale && scaleFactor < 1.0f)) {
                // 最大值最小值判断
                if (scaleFactor * scale < InitSclale) {
                    scaleFactor = InitSclale / scale;
                }
                if (scaleFactor * scale > 3) {
                    scaleFactor = 3 / scale;
                }
                mScaleMatrix.postScale(scaleFactor, scaleFactor,
                        scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                areaScale(scaleFactor, scaleFactor,
                        scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            }
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        }
    }


    private void areaScale(float sx, float sy, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy, px, py);
        for (int i = 0; i < selectedItemList.size(); i++) {
            selectedItemList.get(i).getPath().transform(matrix);
        }
        invalidate();
    }

    private void areaTranslate(float deltaX, float deltaY) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(deltaX, deltaY);
        for (int i = 0; i < selectedItemList.size(); i++) {
            selectedItemList.get(i).getPath().transform(matrix);
        }
        invalidate();
    }

}
