package yt.mvpdemo.base;

/**
 * Created by ${zhangyuanchao} on 2017/12/8.
 * 服务器响应数据的基类
 */

public class BaseResponse<T> {
    public int code;
    public String msg;
    public T data;
}
