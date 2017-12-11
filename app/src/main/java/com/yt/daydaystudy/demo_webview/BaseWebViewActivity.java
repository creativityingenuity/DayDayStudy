package com.yt.daydaystudy.demo_webview;

import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by ${zhangyuanchao} on 2017/10/18.
 */

public abstract class BaseWebViewActivity extends AppCompatActivity {
    public WebView mWebView;

    public void initWebView(WebView mWebView, final ProgressBar mLoading, String url) {
        this.mWebView = mWebView;
        WebSettings settings = mWebView.getSettings();
        mWebView.loadUrl(url);
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDefaultTextEncodingName("utf-8");
        //隐藏缩放控件
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        //Uncaught TypeError: Cannot call method ‘getItem’ of null”, source: url
        //网页加载不完全并报出如上错误时，有可能是你的DOM储存API没有打开，在代码中加上一行：
        settings.setDomStorageEnabled(true);
        // 设置缓存模式：不使用缓存
        // settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                /*设置页面标题
                tvtitle.setText(title);*/
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (null == mLoading) return;
                if (newProgress < 100) {
                    mLoading.setProgress(newProgress);
                } else {
                    mLoading.setVisibility(View.GONE);
                }
            }
        });

        //监听网页的加载操作
        //设置不用系统浏览器打开，不需要重写shouldOverrideUrlLoading，这个方法是去拦截url的。
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //而且如果不需要对url进行拦截做处理，而是简单的继续加载此网址，则建议采用返回false的方式而不是loadUrl的方式进行加载网址
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mWebView) return;
        mWebView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*暂停全局*/
        if (null == mWebView) return;
        mWebView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        if (null == mWebView) return;
        /*清除缓存 历史记录*/
        mWebView.clearCache(true);
        mWebView.clearHistory();
        /*销毁*/
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (null == mWebView) return false;
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
