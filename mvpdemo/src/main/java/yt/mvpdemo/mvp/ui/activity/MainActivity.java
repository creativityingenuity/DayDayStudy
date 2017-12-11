package yt.mvpdemo.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import yt.mvpdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.sss);
        webView.loadUrl("https://sky.zhongan.com/open/qrcode/iybQrcodeProduct?productId=1000688&accountId=502325089");
        webView.setWebViewClient(new WebViewClient() {
            //设置不用系统浏览器打开,直接显示在当前Webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
