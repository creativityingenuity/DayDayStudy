package com.yt.daydaystudy.demo_arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/7/2.
 */
@Interceptor(priority = 3,name = "xxx")
public class DemoInpterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

    }

    @Override
    public void init(Context context) {

    }
}
