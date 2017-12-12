package yt.mvpdemo.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 */

public abstract class BaseActivity extends RxAppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);
}
