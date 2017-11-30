package com.yt.daydaystudy;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test1();
    }

    /**
     * lrucache diskLrucache
     */
    private void test1() {
        /*设置LruCache缓存的大小，一般为当前进程可用容量的1/8*/
        int maxMemoCache = (int) (Runtime.getRuntime().totalMemory() / 1024/8);
        LruCache lruCache = new LruCache<String,Bitmap>(maxMemoCache){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                /*重写sizeOf方法，计算出要缓存的每张图片的大小*/
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };

    }
}
