package yt.mvpdemo.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yt.mvpdemo.MVPApplication;
import yt.myutils.LogUtils;
import yt.myutils.NetworkUtils;


/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 * Retrofit封装类
 * <p>
 * 设置日志拦截器拦截服务器返回的json数据。Retrofit将请求到json数据直接转换成了实体类，但有时候我们需要查看json数据，Retrofit并没有提供直接获取json数据的功能。因此我们需要自定义一个日志拦截器拦截json数据，并输入到控制台。
 * <p>
 * 设置 Http 拦截器，处理缓存问题。通过拦截器拦截Http请求头，为请求头配置缓存信息，包括控制缓存的最大生命值，控制缓存的过期时间。
 * <p>
 * 获取Retrofit实例。通过单利模式获取Retrofit实例。
 */

public class RetrofitHelper {
    /*单例*/
    private static RetrofitHelper instance;
    private static APIServers apiServers;
    private int maxCacheTime = 60 * 60 * 24 * 28;
    private static Retrofit retrofit;

    private RetrofitHelper() {
        //在构造中初始化Retrofit 保证全局单一实例
        initRetrofit();
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public static APIServers getApiServers() {
        if (apiServers == null) apiServers = retrofit.create(APIServers.class);
        return apiServers;
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
        //日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    LogUtils.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存 50M
        Cache cache = new Cache(new File(MVPApplication.getInstance().getExternalCacheDir(), "httpCache"), 1024 * 1024 * 50);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)//设置超市
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重连
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(APIServers.BASEURL)
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiServers = retrofit.create(APIServers.class);
    }

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //1.拦截request，在每个请求发出前，进行拦截，然后判断是否有网，若么有网，直接走缓存
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网时 设置缓存超时时间0小时
                return originalResponse.newBuilder()
                        //max-age 控制缓存的最大生命时间
                        .header("Cache-Control", "public, max-age=" + 0)
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                //无网时 设置超时为4周
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxCacheTime)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
