package com.yt.daydaystudy.takephoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yt.daydaystudy.R;

public class PhotoUploadActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        findViewById(R.id.btn_takephoto).setOnClickListener(this);
        findViewById(R.id.btn_album).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_takephoto:
                break;
            case R.id.btn_album:
                break;
        }
    }
}
