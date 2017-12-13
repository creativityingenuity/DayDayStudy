package yt.mvpdemo.base;

import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 */

public abstract class BaseActivity<T extends BasePresent> extends RxAppCompatActivity{
    private T mPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);
}
