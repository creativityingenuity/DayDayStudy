package com.yt.demo_view.bannerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yt.demo_view.MainActivity;
import com.yt.demo_view.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 循环广告Demo
 * 思路：
 * 我们需要在viewpager上展示5张图片，
 * 但是我们需要循环滑动，所以需要告诉Viewpager一个虚假的个数，eg:100,那么现在我们可以右滑100下了。
 * 当滑倒第6个页面时，这个是空白，所以此时可以把第一屏的内容给第6屏，即从最后一屏循环滑动到了第一屏，
 * 此时我们就实现了向右循环。
 * 同理，当第一屏向前滑动时，我们同样可以返回最后一屏的View。
 * 但是这里有一个问题，第一屏没办法向前滑动，因为第一屏的位置是0，ViewPager不能滑动到-1的位置！
 * 如果在ViewPager将要滑动到0时，这个时候我们迅速将其切换到第6屏会怎么样？
 * 由于第6屏的内容实际上就是第1屏的内容，因此界面上感受不到任何变化，
 * 但是这个时候ViewPager当前屏幕的下标已经从0变为5了，这意味着ViewPager可以向前滑动了。
 * OK 下来就是实现了
 */
public class BannerActivity extends AppCompatActivity {
    private int[] imageResIds = {R.mipmap.ic_9, R.mipmap.ic_8, R.mipmap.ic_7, R.mipmap.ic_6, R.mipmap.ic_5};
    private ViewPager mViewPager;
    private LinearLayout ll_content;
    private boolean isTouch = false;//手指是否触摸
    private int FAKE_SIZE = 100;//随便给的虚假大小，但是不能太大，有可能会发生ANR
    private int TRUE_SIZE = 5;//真实页面个数
    //设置自动循环，有多种实现方式，handler,timer,thread.. 这里我们用Timer实现
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isTouch) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        initData();
    }

    private void initData() {
        mViewPager.setAdapter(mAdapter);
        ImageView point = null;
        for (int i = 0; i < imageResIds.length; i++) {
            //设置小点
            point = new ImageView(this);
            point.setImageResource(R.mipmap.black);
            ll_content.addView(point);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) point.getLayoutParams();
            params.leftMargin = 8;//设置间距
            point.setLayoutParams(params);
        }
        //设置第一个小点为白色
        ((ImageView) ll_content.getChildAt(0)).setImageResource(R.mipmap.white);
        //当页面改变时回调
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        //添加触摸监听
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isTouch = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isTouch = false;
                        break;
                }
                return false;
            }
        });
        //设置自动循环  //安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。
        mTimer.schedule(mTimerTask, 3000, 3000);
    }

    //记录上一个点
    int prePos = 0;
    //设置页面改变监听
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            position %= TRUE_SIZE;
            //设置前一个点为黑的
            ((ImageView) ll_content.getChildAt(prePos)).setImageResource(R.mipmap.black);
            //设置小点颜色
            ImageView point = (ImageView) ll_content.getChildAt(position);
            point.setImageResource(R.mipmap.white);
            prePos = position;
            super.onPageSelected(position);
        }
    };
    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return FAKE_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= TRUE_SIZE;//利用取模来实现不断向后循环
            ImageView mImageView = new ImageView(BannerActivity.this);
            mImageView.setImageResource(imageResIds[position]);
            container.addView(mImageView);
            return mImageView;
        }
        
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            int position = mViewPager.getCurrentItem();
            if (position == 0) {
                position = TRUE_SIZE;
                mViewPager.setCurrentItem(position);
            } else if (position == FAKE_SIZE - 1) {
                position = TRUE_SIZE - 1;
                mViewPager.setCurrentItem(position);
            }
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
        mTimer.cancel();
        super.onDestroy();
    }

    public static void startAction(MainActivity activity) {
        activity.startActivity(new Intent(activity,BannerActivity.class));
    }
}
