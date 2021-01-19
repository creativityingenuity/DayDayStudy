package com.yt.demo_view.DrawColor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.demo_view.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.but_svg).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SVGActivity.class)));
        findViewById(R.id.but_pic).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PicActivity.class)));

    }


}
