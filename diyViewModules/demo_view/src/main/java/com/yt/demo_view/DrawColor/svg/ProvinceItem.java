package com.yt.demo_view.DrawColor.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class ProvinceItem {
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
            // 内层
            paint.clearShadowLayer();
            paint.setColor(drawColor);
            if (StyleColor != null) {
                paint.setColor(Color.parseColor(StyleColor.replace("fill:", "").trim()));
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(2);
                canvas.drawPath(path, paint);
                canvas.save();
            }

        } else {
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
