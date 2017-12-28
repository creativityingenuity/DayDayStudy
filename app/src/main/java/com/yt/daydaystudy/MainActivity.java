package com.yt.daydaystudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yt.daydaystudy.demo_takephoto.PhotoUploadActivity;

import yt.myutils.core.LogUtils;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*后期会换成流式布局*/
        GridView mGridView = (GridView) findViewById(R.id.gv);
        String[] mainStrings = getResources().getStringArray(R.array.main_txt);
        mGridView.setAdapter(new MainGridViewAdapter(mainStrings));
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                LogUtils.e("xxx");
                PhotoUploadActivity.startAction(this);
                break;
            case 1:
                LogUtils.e("xxx");
                break;
            case 2:
                LogUtils.e("xxx");
                break;
            case 3:
                LogUtils.e("xxx");
                break;
            case 4:
                LogUtils.e("xxx");
                break;
        }
    }
}
