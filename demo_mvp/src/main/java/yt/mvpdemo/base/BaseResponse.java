package yt.mvpdemo.base;

/**
 * Created by ${zhangyuanchao} on 2017/12/8.
 * 服务器响应数据的基类
 */

public class BaseResponse<T> {
    public int code;
    public String msg;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断响应是否成功
     * @return
     */
    public boolean isSuccess(){
        return code==1;
    }

//    /**
//     * 响应是否为空
//     * @return
//     */
//    public boolean isEmpty(){
//
//    }
//
//    /**
//     * 是否没有更多
//     * @return
//     */
//    public boolean isNoMore(){
//
//    }
}
