package com.yt.demo_view.DrawColor.svg;

import android.graphics.Path;

/**
 * Description:  SVG绘制信息封装
 */

public class SVGPath {

    /**
     * 绘制路径
     */
    private Path path;

    /**
     * id
     */
    private String id;

    /**
     * lujing
     */
    private String pathD;

    /**
     * color
     */
    private String color;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public SVGPath(String code, String pathData, String color) {
        this.id = code;
        this.pathD = pathData;
        this.color = color;
        path = PathParser.createPathFromPathData(pathData);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
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

    @Override
    public String toString() {
        return "SVGPath{" +
                "path=" + path +
                ", id='" + id + '\'' +
                ", pathD='" + pathD + '\'' +
                '}';
    }


}
