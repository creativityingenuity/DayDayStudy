package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.yt.demo_view.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/***
 // * 1.根据SVG的信息实现绘制
 // * 2.根据SVG的颜色信息，实现点击上色
 // * 3.对上色的步骤进行统计
 // * 4.在画一个自定义View，然后根据统计的步骤,自动画到画布上
 // */
public class MatrixPathView extends View {

    private static final String TAG = "SVGPathView";


    GestureDetector mGestureDetector;
    ScaleGestureDetector mScaleGestureDetector;
    private GestureDetectorCompat gestureDetectorCompat;


    // 画布当前的 Matrix， 用于获取当前画布的一些状态信息，例如缩放大小，平移距离等
    private Matrix mCanvasMatrix = new Matrix();

    // 将用户触摸的坐标转换为画布上坐标所需的 Matrix， 以便找到正确的缩放中心位置
    private Matrix mInvertMatrix = new Matrix();

    // 所有用户触发的缩放、平移等操作都通过下面的 Matrix 直接作用于画布上，
    // 将系统计算的一些初始缩放平移信息与用户操作的信息进行隔离，让操作更加直观
    private Matrix mUserMatrix = new Matrix();


    // 基础的缩放和平移信息，该信息与用户的手势操作无关
    private float mBaseScale;
    private float mBaseTranslateX;
    private float mBaseTranslateY;

    // ----------------------------------------------------------
    private Paint paint;

    private int miniWidth;
    private int miniHeight;
    private int provinceTextSize;
    private int provinceMargin;
    private int numberMargin;
    private int bottomPadding;
    private float scale = 1;
    private RectF mapSize;

    private HashSet<String> hashSet = new HashSet<>();

    private HashMap<String, List<ProvinceItem>> repeatcolorList = new HashMap();

    // 存放路径的list
    private List<ProvinceItem> itemList;

    // 存放路径的list
    private List<ProvinceItem> selectedItemList = new ArrayList<>();
    // 存放svg信息的对象
    private ProvinceItem selectedItem;

    public MatrixPathView(Context context) {
        super(context);
        init(context, null, 0);
        initGesture(context);
    }

    public MatrixPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
        initGesture(context);
    }

    public MatrixPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }


    private void init(Context context, AttributeSet attrs, int defStyle) {
        paint = new Paint();
        paint.setAntiAlias(true);
        miniWidth = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_width);
        miniHeight = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_height);
        provinceTextSize = getResources().getDimensionPixelSize(R.dimen.map_province_text_size);
        provinceMargin = getResources().getDimensionPixelSize(R.dimen.map_province_margin);
        numberMargin = getResources().getDimensionPixelSize(R.dimen.map_number_margin);
        initGesture(context);


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
                    // number 标识

                    if (hashSet.add(provincePath.getColor())) {
                        List<ProvinceItem> repeatlist = new ArrayList<>();
                        repeatlist.add(item);
                        repeatcolorList.put(provincePath.getColor(), repeatlist);
                        Log.e(TAG, "星星 Frist one  color:" + provincePath.getColor() + "   and  " + repeatcolorList.get(provincePath.getColor()).size());
//                        item.setTypeNumber(1);
                    } else {
                        repeatcolorList.get(provincePath.getColor()).add(item);
                        Log.e(TAG, "星星 color:" + provincePath.getColor() + "   and  " + repeatcolorList.get(provincePath.getColor()).size());
//                        item.setTypeNumber(repeatcolorList.get(provincePath.getColor()).size());
                    }

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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int viewWidth = width;
        int viewHeight = height;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                viewWidth = width > miniWidth ? width : miniWidth;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewWidth = miniWidth;
                break;
        }

        int computeHeight;
        if (mapSize != null) {
            double mapWidth = mapSize.width();
            double mapHeight = mapSize.height();
            scale = (float) (viewWidth / mapWidth);
            computeHeight = (int) (mapHeight * viewWidth / mapWidth);
        } else {
            computeHeight = (miniHeight * viewWidth / miniWidth);
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                viewHeight = height;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                viewHeight = miniHeight > computeHeight ? miniHeight : computeHeight;
                break;
        }

        if (mapSize != null) {
            double mapWidth = mapSize.width();
            scale = (float) (viewWidth / mapWidth);
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(viewHeight + bottomPadding, MeasureSpec.EXACTLY));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getMeasuredWidth() * 1.0f / getMeasuredHeight() > w * 1.0f / h) {
            mBaseScale = w * 1.0f / getMeasuredWidth();
            mBaseTranslateX = 0;
            mBaseTranslateY = (h - getMeasuredHeight() * mBaseScale) / 2;
        } else {
            mBaseScale = h * 1.0f / getMeasuredHeight() * 1.0f;
            mBaseTranslateX = (w - getMeasuredWidth() * mBaseScale) / 2;
            mBaseTranslateY = 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画布的缩放
        canvas.translate(mBaseTranslateX, mBaseTranslateY);
        canvas.scale(mBaseScale, mBaseScale);

        canvas.save();
        canvas.concat(mUserMatrix);

        mCanvasMatrix = canvas.getMatrix();
        mCanvasMatrix.invert(mInvertMatrix);

        final List<ProvinceItem> list = itemList;
        if (list != null) {
//
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
                // 把所有的颜色归类,然后绘制到图层上
                paint.setTypeface(Typeface.DEFAULT);
                paint.setColor(0xFF333333);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.clearShadowLayer();
                paint.setTextSize(provinceTextSize);

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            fixTranslate();
        }
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


    //--- Tools ------------------------------------------------------------------------------------

    //--- 将坐标转换为画布坐标 ---
    private float[] mapPoint(float x, float y, Matrix matrix) {
        float[] temp = new float[2];
        temp[0] = x;
        temp[1] = y;
        matrix.mapPoints(temp);
        return temp;
    }

    private float[] mapVectors(float x, float y, Matrix matrix) {
        float[] temp = new float[2];
        temp[0] = x;
        temp[1] = y;
        matrix.mapVectors(temp);
        return temp;
    }


    //--- 获取 Matrix 中的属性 ---
    private float[] matrixValues = new float[9];
    private static final int MSCALE_X = 0, MSKEW_X = 1, MTRANS_X = 2;
    private static final int MSKEW_Y = 3, MSCALE_Y = 4, MTRANS_Y = 5;
    private static final int MPERSP_0 = 6, MPERSP_1 = 7, MPERSP_2 = 8;

    @IntDef({MSCALE_X, MSKEW_X, MTRANS_X, MSKEW_Y, MSCALE_Y, MTRANS_Y, MPERSP_0, MPERSP_1, MPERSP_2})
    @Retention(RetentionPolicy.SOURCE)
    private @interface MatrixName {
    }

    private float getMatrixValue(@MatrixName int name, Matrix matrix) {
        matrix.getValues(matrixValues);
        return matrixValues[name];
    }

    //--- 限制缩放比例 ---
    private static final float MAX_SCALE = 4.0f;    //最大缩放比例
    private static final float MIN_SCALE = 0.5f;    // 最小缩放比例

    private float getRealScaleFactor(float currentScaleFactor) {
        float realScale = 1.0f;
        float userScale = getMatrixValue(MSCALE_X, mUserMatrix);    // 用户当前的缩放比例
        float theoryScale = userScale * currentScaleFactor;           // 理论缩放数值

        // 如果用户在执行放大操作并且理论缩放数据大于4.0
        if (currentScaleFactor > 1.0f && theoryScale > MAX_SCALE) {
            realScale = MAX_SCALE / userScale;
        } else if (currentScaleFactor < 1.0f && theoryScale < MIN_SCALE) {
            realScale = MIN_SCALE / userScale;
        } else {
            realScale = currentScaleFactor;
        }
        return realScale;
    }


    private float chageX = 0.0f;
    private float chageY = 0.0f;
    //--- 手势处理 ----------------------------------------------------------------------------------

    private void initGesture(Context context) {
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                Log.e(TAG, "坐标x gestureDetectorCompat-->" + e.getX() + " , 坐标Y -->" + e.getY());
                if (chageY > 0.0f || chageX > 0.0f) {
                    Log.e(TAG, "坐标x gestureDetectorCompat- 改编后->" +chageX + " , 坐标Y -->" + chageY);

                    handlerTouch((int) chageY, (int) chageY);
                } else {
                    handlerTouch((int) x, (int) y);
                }
                return true;
            }
        });

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float scale = getMatrixValue(MSCALE_X, mCanvasMatrix);
                mUserMatrix.preTranslate(-distanceX / scale, -distanceY / scale);
                Log.e(TAG, "坐标x initGesture-->" + -distanceX / scale + " , 坐标Y -->" + -distanceY / scale);
                //fixTranslate();   // 在用户滚动时不进行修正，保证用户滚动时也有响应， 在用户抬起手指后进行修正
                invalidate();
                return true;
            }

        });

        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float fx = detector.getFocusX();
                float fy = detector.getFocusY();
                float[] points = mapPoint(fx, fy, mInvertMatrix);
                chageX = fx;
                chageY = fy;
                Log.e(TAG, "坐标x mScaleGestureDetector-->" + fx + " , 坐标Y -->" + fy);
                scaleFactor = getRealScaleFactor(scaleFactor);
                mUserMatrix.preScale(scaleFactor, scaleFactor, points[0], points[1]);
                fixTranslate();
                invalidate();
                return true;
            }

        });
    }

    // 修正缩放
    private void fixTranslate() {
        // 对 Matrix 进行预计算，并根据计算结果进行修正
        Matrix viewMatrix = getMatrix();    // 获取当前控件的Matrix
        viewMatrix.preTranslate(mBaseTranslateX, mBaseTranslateY);
        viewMatrix.preScale(mBaseScale, mBaseScale);
        viewMatrix.preConcat(mUserMatrix);
        Matrix invert = new Matrix();
        viewMatrix.invert(invert);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);

        float userScale = getMatrixValue(MSCALE_X, mUserMatrix);
        float scale = getMatrixValue(MSCALE_X, viewMatrix);

        float[] center = mapPoint(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, viewMatrix);
        float distanceX = center[0] - getWidth() / 2.0f;
        float distanceY = center[1] - getHeight() / 2.0f;
        float[] wh = mapVectors(getMeasuredWidth(), getMeasuredHeight(), viewMatrix);

        if (userScale <= 1.0f) {
            mUserMatrix.preTranslate(-distanceX / scale, -distanceY / scale);
        } else {
            float[] lefttop = mapPoint(0, 0, viewMatrix);
            float[] rightbottom = mapPoint(getMeasuredWidth(), getMeasuredHeight(), viewMatrix);

            // 如果宽度小于总宽度，则水平居中
            if (wh[0] < getWidth()) {
                mUserMatrix.preTranslate(distanceX / scale, 0);
            } else {
                if (lefttop[0] > 0) {
                    mUserMatrix.preTranslate(-lefttop[0] / scale, 0);
                } else if (rightbottom[0] < getWidth()) {
                    mUserMatrix.preTranslate((getWidth() - rightbottom[0]) / scale, 0);
                }

            }
            // 如果高度小于总高度，则垂直居中
            if (wh[1] < getHeight()) {
                mUserMatrix.preTranslate(0, -distanceY / scale);
            } else {
                if (lefttop[1] > 0) {
                    mUserMatrix.preTranslate(0, -lefttop[1] / scale);
                } else if (rightbottom[1] < getHeight()) {
                    mUserMatrix.preTranslate(0, (getHeight() - rightbottom[1]) / scale);
                }
            }
        }
        invalidate();
    }

}
