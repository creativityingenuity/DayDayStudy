package com.yt.daydaystudy.test_01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yt.daydaystudy.R;

/**
 * 页面状态View
 * 加载成功，加载中，加载出错，没有网络，没有数据五种状态
 */

public class NetworkStateView extends RelativeLayout {
    //当前的加载状态
    private int mCurrentState;
    private static final int STATE_SUCCESS = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_NETWORK_ERROR = 2;
    private static final int STATE_NO_NETWORK = 3;
    private static final int STATE_EMPTY = 4;

    private int mLoadingViewId;

    private int mErrorViewId;

    private int mNoNetworkViewId;

    private int mEmptyViewId;

    private View mLoadingView;
    private View mErrorView;
    private View mNoNetworkView;
    private View mEmptyView;

    private LayoutInflater mInflater;

    private OnRefreshListener mRefreshListener;

    public NetworkStateView(@NonNull Context context) {
        this(context, null);
    }

    public NetworkStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NetworkStateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkStateView);
        mLoadingViewId = typedArray.getResourceId(R.styleable.NetworkStateView_loadingView, 0);
        mErrorViewId = typedArray.getResourceId(R.styleable.NetworkStateView_errorView, 0);
        mNoNetworkViewId = typedArray.getResourceId(R.styleable.NetworkStateView_noNetworkView, 0);
        mEmptyViewId = typedArray.getResourceId(R.styleable.NetworkStateView_emptyView, 0);
        typedArray.recycle();

        mInflater = LayoutInflater.from(context);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showSuccess();
    }

    /**
     * 加载成功表示用来显示Activity或Fragment的界面，并用变量mCurrentState来记住当前显示的状态隐藏加载状态的View
     */
    public void showSuccess() {
        mCurrentState = STATE_SUCCESS;
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载中的状态
     */
    public void showLoading() {
        mCurrentState = STATE_LOADING;
        if (null == mLoadingView) {
            mLoadingView = mInflater.inflate(mLoadingViewId, null);
            addView(mLoadingView);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载失败(网络错误)状态
     */
    public void showError() {
        mCurrentState = STATE_NETWORK_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewId, null);
            mErrorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mRefreshListener) {
                        mRefreshListener.onRefresh();
                    }
                }
            });
            addView(mErrorView);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示没有网络状态
     */
    public void showNoNetwork() {
        mCurrentState = STATE_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = mInflater.inflate(mNoNetworkViewId, null);
            mNoNetworkView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mRefreshListener) {
                        mRefreshListener.onRefresh();
                    }
                }
            });
            addView(mNoNetworkView);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示无数据状态
     */
    public void showEmpty() {
        mCurrentState = STATE_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = mInflater.inflate(mEmptyViewId, null);
            mEmptyView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mRefreshListener) {
                        mRefreshListener.onRefresh();
                    }
                }
            });
            addView(mEmptyView);
        }
        showViewByState(mCurrentState);
    }

    private void showViewByState(int state) {

        //如果当前状态为加载成功，隐藏此View，反之显示
        this.setVisibility(state == STATE_SUCCESS ? View.GONE : View.VISIBLE);

        if (null != mLoadingView) {
            mLoadingView.setVisibility(state == STATE_LOADING ? View.VISIBLE : View.GONE);
        }

        if (null != mErrorView) {
            mErrorView.setVisibility(state == STATE_NETWORK_ERROR ? View.VISIBLE : View.GONE);
        }

        if (null != mNoNetworkView) {
            mNoNetworkView.setVisibility(state == STATE_NO_NETWORK ? View.VISIBLE : View.GONE);
        }

        if (null != mEmptyView) {
            mEmptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 界面刷新操作
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
