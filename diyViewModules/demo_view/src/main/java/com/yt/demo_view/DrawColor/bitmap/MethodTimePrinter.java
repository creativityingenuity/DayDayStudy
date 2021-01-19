package com.yt.demo_view.DrawColor.bitmap;

import android.graphics.Color;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by wsc64 on 2017/7/15.
 */

public class MethodTimePrinter {
    public static final String TAG = "MethodTimePrinter";
    private static long startTime = 0L;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void end(String method) {
        Log.e(TAG, method + " -- \t" + (System.currentTimeMillis() - startTime) + " ms");
    }

    public static void printPixels(int[] pixels) {
        HashMap<String, Integer> maps = new HashMap<>();
        for (int pixel : pixels) {
            String s = "a:" + Color.alpha(pixel) + ", r:" + Color.red(pixel) + ", g:" + Color.green(pixel) + ", b:" + Color.blue(pixel);
            if (!maps.containsKey(s)) {
                maps.put(s, new Integer(0));
            }
            maps.put(s, maps.get(s) + 1);
        }
        for (String s : maps.keySet()) {
            Log.e(TAG, s + "  --  count:" + maps.get(s));
        }
    }
}
