package yt.mvpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yt.mvpdemo.commen.AppManager;
import yt.mvpdemo.commen.RxManager;

/**
 * Created by ${zhangyuanchao} on 2017/12/12.
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {
    public View rootView;
    public LayoutInflater inflater;
    public P mPresenter;
    public M mModel;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.inflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
        }
        mPresenter = AppManager.getAppManager().getT(this, 0);
        mModel = AppManager.getAppManager().getT(this, 1);
        initPresenter();
        return rootView;
    }

    /**
     * 初始化presenter
     */
    protected void initPresenter() {
        if (mPresenter != null && mModel != null) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDetachVM();
        }
    }
}
