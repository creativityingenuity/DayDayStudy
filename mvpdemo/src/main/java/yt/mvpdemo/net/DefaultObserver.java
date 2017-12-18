package yt.mvpdemo.net;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;
import yt.mvpdemo.R;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.commen.RxManager;
import yt.myutils.ToastUtils;

/**
 * Created by ${zhangyuanchao} on 2017/12/11.
 * 处理服务器数据响应
 */
public abstract class DefaultObserver<T extends BaseResponse> extends DisposableObserver<T> {

    @Override
    public void onNext(@NonNull T response) {
        if (response.isSuccess()) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onException(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 服务器返回的数据
     *
     * @param response
     */
    protected void onFail(T response) {
        String msg = response.getMsg();
        if (TextUtils.isEmpty(msg)) {
            ToastUtils.showLong(R.string.error_server);
        } else {
            ToastUtils.showLong(msg);
        }
    }

    /**
     * 请求成功 code为1
     *
     * @param response 服务器返回的数据
     */
    protected abstract void onSuccess(T response);

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(Throwable reason) {
        if (reason instanceof HttpException) {
            ToastUtils.showLong(R.string.error_http);
        } else if (reason instanceof ConnectException || reason instanceof UnknownHostException) {
            ToastUtils.showLong(R.string.error_conn);
        } else if (reason instanceof InterruptedIOException) {   //  连接超时
            ToastUtils.showLong(R.string.error_conn_over_time);
        } else if (reason instanceof JsonParseException
                || reason instanceof JSONException
                || reason instanceof ParseException) {   //  解析错误
            ToastUtils.showLong(R.string.error_parse);
        } else {
            ToastUtils.showLong(R.string.error_unknow);
        }
    }
}
