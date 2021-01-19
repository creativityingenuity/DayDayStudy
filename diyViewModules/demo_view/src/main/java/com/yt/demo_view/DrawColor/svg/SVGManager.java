package com.yt.demo_view.DrawColor.svg;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.color.apply.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SVGManager {

    private static final String TAG = "SVGManager";

    private Context mContext;

    private static SVGManager instance;

    private Handler mMainHandler;

    private RectF mTotalRect;

    private static final Object Lock = new Object();


    private volatile List<SVGPath> mProvincePathList;


    private SVGManager(Context context) {
        mContext = context.getApplicationContext();
        mMainHandler = new Handler(Looper.getMainLooper());
        init();
    }


    public static SVGManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SVGManager.class) {
                if (instance == null) {
                    instance = new SVGManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


    /**
     * 异步获取ProvincePath信息并设置回掉
     *
     * @param callback 封装完成回掉
     */
    public void getProvincePathListAsync(final Callback callback) {
        if (mProvincePathList == null) {
            new Thread(() -> {
                try {
                    synchronized (Lock) {
                        if (mProvincePathList == null) {
                            Lock.wait();
                        }
                    }
                    mMainHandler.post(() -> callback.onResult(mProvincePathList, mTotalRect));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        } else {
            mMainHandler.post(() -> callback.onResult(mProvincePathList, mTotalRect));
        }

    }


    /**
     * 初始化  xml解析svg文件封装ProvincePath信息
     */
    private void init() {
        new Thread(() -> {
            try {
                if (mProvincePathList == null) {
                    long startTime = System.currentTimeMillis();
                    if (mProvincePathList == null) {
                        List<SVGPath> list = new ArrayList<>();
                        InputStream inputStream = mContext.getResources().openRawResource(R.raw.f);
                        XmlPullParser parser = XmlPullParserFactory.newInstance()
                                .newPullParser();
                        parser.setInput(inputStream, "utf-8");
                        int eventType;

                        float left = -1;
                        float right = -1;
                        float top = -1;
                        float bottom = -1;
                        while ((eventType = parser.getEventType()) !=
                                XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_TAG) {
                                String name = parser.getName();
                                if ("path".equals(name)) {
                                    String id = parser.getAttributeValue(null, "id");
                                    String pathData = parser.getAttributeValue(null, "d");
                                    String styleColor = parser.getAttributeValue(null, "style");
                                    SVGPath provincePath = new SVGPath(id, pathData, styleColor);
                                    Path path = provincePath.getPath();

                                    RectF rect = new RectF();
                                    path.computeBounds(rect, true);

                                    left = left == -1 ? rect.left : Math.min(left, rect.left);
                                    right = right == -1 ? rect.right : Math.max(right, rect.right);
                                    top = top == -1 ? rect.top : Math.min(top, rect.top);
                                    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);
                                    list.add(provincePath);
                                }
                            }
                            parser.next();
                        }
                        mTotalRect = new RectF(left, top, right, bottom);
                        mProvincePathList = list;
                    }
                    Log.i(TAG, "初始化结束->" + (System.currentTimeMillis() - startTime));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            synchronized (Lock) {
                Lock.notifyAll();
            }
        }).start();
    }


    public interface Callback {

        void onResult(List<SVGPath> provincePathList, RectF size);

    }
}
