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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yt.mvpdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
        test1();
        test3();
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
                        return  Drawable.createFromStream(new URL(s).openStream(), "src");
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
