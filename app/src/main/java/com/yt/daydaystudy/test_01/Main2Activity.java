package com.yt.daydaystudy.test_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.daydaystudy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            JSONObject jsonObject = new JSONObject("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

