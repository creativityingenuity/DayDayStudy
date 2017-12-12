package yt.mvpdemo.net;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yt.mvpdemo.base.BaseResponse;

/**
 * Created by ${zhangyuanchao} on 2017/12/1.
 * 全局url链接
 *  key=48ecf289b4a3f977bb33e2d7eec91843&request_time=1512104887277&userName=15555555555&passWord=123456
 *  {"code":1,"msg":"\u767b\u9646\u6210\u529f\uff01","data":{"mid":"182","avatar":"\/Upload\/imgs\/avatar\/adv7861510557231.jpg","gender":"1","name":"\u5362\u5fd7\u6d9b","m_number":"0094170711","branch_shop_number":"7230170711","member_level":"3","ispay_pwd":1,"encodes":"\/Upload\/qrcode\/claile692d052a427c3d983a41f570040641b6.png"}}
 */

public interface APIServers {
    String BASEURL = "http://bxht.51bpc.cn";


    @FormUrlEncoded
    @POST("/Api/Member/login")
    Observable<BaseResponse> login(@Field("key") String key,
                                   @Field("request_time") String request_time,
                                   @Field("userName") String userName,
                                   @Field("passWord") String passWord);
}
