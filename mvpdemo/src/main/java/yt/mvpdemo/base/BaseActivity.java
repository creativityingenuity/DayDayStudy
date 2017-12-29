package yt.mvpdemo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;
import yt.mvpdemo.commen.AppManager;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 */

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity{
    /*具体P由子类确定*/
    protected P mPresenter;
    /*具体M由子类确定*/
    private M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mModel = AppManager.getAppManager().getT(this, 0);
        mPresenter = AppManager.getAppManager().getT(this, 1);
        ButterKnife.bind(this);
        initPresenter();
        init(savedInstanceState);
    }

    /**
     * 初始化presenter
     */
    protected void initPresenter() {
        if (mPresenter != null && mModel != null) {
            mPresenter.attachVM(this, mModel);
        }
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetachVM();
        }
        AppManager.getAppManager().finishActivity(this);
    }
}
