package com.yt.daydaystudy.demo_webview;

import android.os.Bundle;
import android.webkit.JavascriptInterface;

/**
 * 简单通用的h5
 */

public class CommonWebActivity extends BaseWebViewActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initWebView(webview, mLoading, url, tvTitle);
    }

    /*android调用js mWebView.loadUrl("javascript:del()");*/
    /*js调用Android  */
    class AndroidToJs {
        /*支付*/
        @JavascriptInterface
        public void go_pay(String status, String id, String num) {

        }

        /*关闭界面*/
        @JavascriptInterface
        public void closeWeb() {
            CommonWebActivity.this.finish();
        }

        /**
         * 拨打电话
         */
        @JavascriptInterface
        public void call(String phone) {

        }
    }
}
