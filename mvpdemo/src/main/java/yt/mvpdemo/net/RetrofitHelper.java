package yt.mvpdemo.net;

import android.content.Context;

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
    /*主机host*/
    private final String BASEURL = "";
    private Context mContext;
    private APIServers apiServers;

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
        Cache cache = new Cache(new File(mContext.getExternalCacheDir(), "httpCache"), 1024 * 1024 * 50);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)//设置超市
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重连
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
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
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
