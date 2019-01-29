package com.yt.demo_view.lockview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.demo_view.R;

public class LockScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity,LockScreenActivity.class));
    }
}
