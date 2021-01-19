package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
public class SVGPathView extends View {

    private static final String TAG = "SVGPathView";


    private Paint paint;

    private int miniWidth;
    private int miniHeight;
    private int provinceTextSize;
    private int provinceMargin;
    private int numberMargin;
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

    public SVGPathView(Context context) {
        super(context);
        init(null, 0);
    }

    public SVGPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SVGPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {

        paint = new Paint();
        paint.setAntiAlias(true);
        miniWidth = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_width);
        miniHeight = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_height);
        provinceTextSize = getResources().getDimensionPixelSize(R.dimen.map_province_text_size);
        provinceMargin = getResources().getDimensionPixelSize(R.dimen.map_province_margin);
        numberMargin = getResources().getDimensionPixelSize(R.dimen.map_number_margin);


        gestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                float x = e.getX();
                float y = e.getY();
                handlerTouch((int) x, (int) y);
                return true;
            }
        });


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
                paint.setTextSize(provinceTextSize);
                // 获取路径
                //      String provinceName = selectedItem.getPathD();
                // 把路径画到画布上面
                // canvas.drawText(provinceName, width / 2, provinceMargin, paint);

                //     canvas.drawText("", width / 2, provinceMargin + provinceTextSize + numberMargin, paint);

            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetectorCompat.onTouchEvent(event);
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


    /**
     * SVG 绘制出来的路径
     */
    public static class ProvinceItem {
        /**
         * 区域路径
         */
        private Path path;

        /**
         * 区域背景色，默认白色
         */
        private int drawColor = Color.WHITE;

        /**
         * id
         */
        private String id;

        /**
         * 路径
         */
        private String pathD;

        private String StyleColor;

        public String getStyleColor() {
            return StyleColor;
        }

        public void setStyleColor(String styleColor) {
            StyleColor = styleColor;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPathD() {
            return pathD;
        }

        public void setPathD(String pathD) {
            this.pathD = pathD;
        }

        /**
         * 区域绘制方法
         *
         * @param canvas     画布
         * @param paint      画笔
         * @param isSelected 是否选中
         */
        void drawItem(Canvas canvas, Paint paint, boolean isSelected) {

            // 区域选中时绘制阴影描边效果,目前隐藏描边效果
            if (isSelected) {
                // 选中外层
//                paint.setStrokeWidth(2);
//                paint.setColor(Color.BLACK);
//                paint.setStyle(Paint.Style.FILL);
//                paint.setShadowLayer(8, 0, 0, 0xFFFFFFFF);
//                canvas.drawPath(path, paint);
                // 内层
                paint.clearShadowLayer();
                paint.setColor(drawColor);
                if (StyleColor != null) {
                    Log.e(TAG, "Color---> " + StyleColor.replace("fill:", "").trim());
                    paint.setColor(Color.parseColor(StyleColor.replace("fill:", "").trim()));
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(2);
                    canvas.drawPath(path, paint);
                    canvas.save();
                } else {
                    Log.e(TAG, "我是空的，不设置颜色");
                }

            } else {
                //非选中时，绘制描边效果
//                paint.clearShadowLayer();
//                paint.setStrokeWidth(1);
//                paint.setStyle(Paint.Style.FILL);
//                paint.setColor(drawColor);
//                canvas.drawPath(path, paint);
                // 新的画布,画SVG的图层
                paint.setStyle(Paint.Style.STROKE);
                int strokeColor = 0xFFD0E8F4;
                paint.setColor(strokeColor);
                canvas.drawPath(path, paint);
            }
        }

        /**
         * 判断该区域是否处于touch状态
         *
         * @param x 当前x
         * @param y 当前y
         * @return 是否处于touch状态
         */
        boolean isTouched(int x, int y) {
            RectF r = new RectF();
            path.computeBounds(r, true);

            Region region = new Region();
            region.setPath(path, new Region((int) r.left, (int) r.top, (int) r.right, (int) r.bottom));
            return region.contains(x, y);
        }


        public Path getPath() {
            return path;
        }

        void setPath(Path path) {
            this.path = path;
        }

        public int getDrawColor() {
            return drawColor;
        }

        void setDrawColor(int drawColor) {
            this.drawColor = drawColor;
        }

    }

    // 把当前选中的东西view都返回给上级
    public List<ProvinceItem> getSelectedItemList() {
        if (selectedItemList.size() > 0) {
            return selectedItemList;
        }
        return null;
    }


}
