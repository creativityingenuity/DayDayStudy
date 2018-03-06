package yt.mvpdemo.mvp.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.R;
import yt.mvpdemo.base.BaseResponse;
import yt.mvpdemo.commen.RxManager;
import yt.mvpdemo.mvp.model.entity.LoginEntity;
import yt.mvpdemo.mvp.model.entity.RegisterEntity;
import yt.mvpdemo.net.DefaultObserver;
import yt.mvpdemo.net.NetHelper;
import yt.myutils.core.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*rxjava练习*/
//        scene1();
//        scene2();

        /*简单练习*/
//        test4();
        test5();
    }

    private void test5() {
        Observable.zip(new ObservableSource<Integer>() {
            @Override
            public void subscribe(Observer<? super Integer> observer) {
                    observer.onNext(1);
                    observer.onNext(2);
                    observer.onNext(3);
                    observer.onNext(4);
            }
        }, new ObservableSource<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {
                observer.onNext("A");
                observer.onNext("B");
                observer.onNext("C");
                observer.onNext("D");
            }
        }, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return null;
            }
        });
        Observable.just(1,2,3)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return 10*integer;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });


        Observable.zip(Observable.just(1, 2, 3,5,78,8,9,0), Observable.just("A","B","C"),
                new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                        return integer+s;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        LogUtils.e(s);
                    }
                });
    }

    private void test4() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer) throws Exception {
                return integer * 10;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                LogUtils.e(integer);
            }
        });
    }

    /**
     * 场景2：
     * 使用zip对并发的请求做处理
     * 假设在用户页面，不仅要显示用户的普通信息，还要显示用户的资金信息。
     * 这两个信息分别对应的两个接口，同时访问两个接口，当两个信息都获取成功的时候才算成功，如果有一个没获取成功则算失败，需要重新获取。
     */
    private void scene2() {
        Observable.zip(NetHelper.getApiServers().regist(), NetHelper.getApiServers().login("", "", "", ""), new BiFunction<BaseResponse<RegisterEntity>, BaseResponse<LoginEntity>, String>() {
            @Override
            public String apply(@NonNull BaseResponse<RegisterEntity> registerEntityBaseResponse, @NonNull BaseResponse<LoginEntity> loginEntityBaseResponse) throws Exception {
                return null;
            }
        });
    }

    /**
     * 场景1：在注册成功后调用登录接口
     */
    private void scene1() {
        NetHelper.getApiServers()
                .regist()//注册
                .flatMap(new Function<BaseResponse<RegisterEntity>, ObservableSource<BaseResponse<LoginEntity>>>() {
                    @Override
                    public ObservableSource<BaseResponse<LoginEntity>> apply(@NonNull BaseResponse<RegisterEntity> registerEntityBaseResponse) throws Exception {
                        return NetHelper.getApiServers().login("", "", "", "");//登录
                    }
                })
                /*2.使用compose简化代码*/
                .compose(RxManager.<BaseResponse<LoginEntity>>rxSchedulerHelper())
                .subscribe(new DefaultObserver<BaseResponse<LoginEntity>>() {
                    @Override
                    protected void onSuccess(BaseResponse<LoginEntity> response) {
                        //登录成功
                    }
                });
    }

    private void test3() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        e.onNext("http://xxxxx");
                        e.onComplete();
                    }
                })
                .map(new Function<String, Drawable>() {
                    @Override
                    public Drawable apply(@NonNull String s) throws Exception {
                        return Drawable.createFromStream(new URL(s).openStream(), "src");
                    }
                })
                /*这个是1对多 进行变换 例如一个员工负责多个任务，现在要打印所有员工的所有任务 嵌套异步操作
                .flatMap(new Function<Drawable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Drawable drawable) throws Exception {
                        return null;
                    }
                })*/
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Drawable s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*
        * just-form
        * filter
        * compose 代替 flatMap
        * first  只发送符合条件的第一个事件。
        *
        * */
    }

    private void test1() {
//        action  简化整体流程  2中进行了修改 等会弄
        Observable.just("xxx").subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {

            }
        });
    }

    private void test() {
        //#事件产生
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("");
                e.onComplete();
            }
        });
        /*简化写法*/
        Observable<String> helloworld = Observable.just("helloworld");

        //#事件消费
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);//hellow
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                //每接收到一个事件，回调一次
            }

            @Override
            public void onError(Throwable t) {
                //事件处理异常回调
            }

            @Override
            public void onComplete() {
                //事件全部处理完成后回调
            }
        };
    }
}
