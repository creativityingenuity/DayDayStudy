package com.yt.demo_view.DrawColor;

import android.app.Application;


public class SVGApplication extends Application {


    private static SVGApplication svgApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        svgApplication = this;
        //init
    }



    public static SVGApplication getApp() {
        return svgApplication;
    }

}
