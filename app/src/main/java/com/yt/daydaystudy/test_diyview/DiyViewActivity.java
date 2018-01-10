package com.yt.daydaystudy.test_diyview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.daydaystudy.R;

public class DiyViewActivity extends AppCompatActivity {
    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, DiyViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view);
    }
}
