package yt.mvpdemo.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import yt.mvpdemo.commen.AppManager;
import yt.mvpdemo.commen.RxManager;
import yt.mvpdemo.commen.StatusBarCompat;

/**
 * Created by ${zhangyuanchao} on 2017/11/30.
 */

public abstract class BaseActivity<M extends BaseModel,P extends BasePresenter> extends RxAppCompatActivity {
    public P mPresenter;
    public M mModel;
    public RxManager mRxManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        mRxManager = new RxManager();
        mModel = AppManager.getAppManager().getT(this, 0);
        mPresenter = AppManager.getAppManager().getT(this, 1);
        ButterKnife.bind(this);
        initPresenter();
        init(savedInstanceState);
    }

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();
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
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor();
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.black));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDetachVM();
        }
        AppManager.getAppManager().finishActivity(this);

    }
}
