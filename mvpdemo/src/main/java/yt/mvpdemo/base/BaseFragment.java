package yt.mvpdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import yt.mvpdemo.commen.AppManager;

/**
 * Created by ${zhangyuanchao} on 2017/12/12.
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment {
    public View rootView;
    protected P mPresenter;
    private M mModel;
    private Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutId(), container, false);
        }
        ButterKnife.bind(this, rootView);
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
        ButterKnife.unbind(this);
    }
}
