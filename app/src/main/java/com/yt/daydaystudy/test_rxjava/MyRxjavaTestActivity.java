package com.yt.daydaystudy.test_rxjava;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/7/12.
 */

public class MyRxjavaTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test1();
        //flatmap 解决嵌套

        test2();
    }

    private void test2() {
        Flowable.range(0,10)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void test1() {
        Observable.just(getFilePath())
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {

                        return createBitmapFromPath(s);
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {

                    }
                });
    }

    private Bitmap createBitmapFromPath(String s) {
        return null;
    }

    private String getFilePath() {
        return "";
    }
}
