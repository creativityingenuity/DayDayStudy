package com.yt.daydaystudy.demo_statusbarcolor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.daydaystudy.R;

/**
 * 沉浸式状态栏 demo演示
 */
public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity,StatusBarActivity.class));
    }
}
