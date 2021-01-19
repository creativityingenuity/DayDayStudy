package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.widget.ImageView;

import com.yt.demo_view.R;

import java.util.ArrayList;
import java.util.List;

public class AutoSVGPathView extends ImageView {

    private List<ProvinceItem> items;
    private List<ProvinceItem> list = new ArrayList<>();
    private Paint paint;

    private int bottomPadding;
    private float scale = 1;
    private RectF mapSize;
    private int miniWidth;
    private int miniHeight;
    private int provinceTextSize;


    public AutoSVGPathView(Context context, List<ProvinceItem> provinceItems) {
        super(context);
        this.items = provinceItems;
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        miniWidth = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_width);
        miniHeight = getContext().getResources().getDimensionPixelSize(R.dimen.map_min_height);
        provinceTextSize = getResources().getDimensionPixelSize(R.dimen.map_province_text_size);

        if (!isInEditMode()) {
            //获取地图svg封装信息
            SVGManager.getInstance(getContext()).getProvincePathListAsync((provincePathList, size) -> {

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

                //刷新布局
                requestLayout();
                postInvalidate();
            });
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (items != null && items.size() > 0) {
            // 开始绘制到新的上面
            canvas.save();
            canvas.scale(scale, scale);
            // 先画一个空的图
            for (ProvinceItem item : list) {
                item.drawItem(canvas, paint, false);
            }
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    // 开始绘制上色每一个view,启用动画效果,造成视觉差
                    items.get(i).drawItem(canvas, paint, true);
                }
            }
            canvas.restore();
            if (items != null) {
                paint.setTypeface(Typeface.DEFAULT);
                paint.setColor(0xFF333333);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.clearShadowLayer();
                paint.setTextSize(provinceTextSize);
            }

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
}
