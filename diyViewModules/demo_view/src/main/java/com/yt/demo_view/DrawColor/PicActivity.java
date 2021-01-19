package com.yt.demo_view.DrawColor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yt.demo_view.DrawColor.bitmap.ColoringView;
import com.yt.demo_view.DrawColor.utils.ImgHelper;
import com.yt.demo_view.R;

public class PicActivity extends AppCompatActivity {

    private ColoringView colorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        colorImageView = findViewById(R.id.colorImageView);
        colorImageView.setImageByBitmap(ImgHelper.getBitmapFormResources(this, R.mipmap.ijshdjsh), false);
    }
}
