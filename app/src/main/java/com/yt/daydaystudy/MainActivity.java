package com.yt.daydaystudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yt.daydaystudy.demo_arouter.ArouterDemoActivity;
import com.yt.daydaystudy.demo_greendao.GreenDaoActivity;
import com.yt.daydaystudy.demo_recyclerview.RecyclerViewDemoActivity;
import com.yt.daydaystudy.demo_statusbarcolor.StatusBarActivity;
import com.yt.daydaystudy.demo_takephoto.PhotoUploadActivity;
import com.yt.daydaystudy.test_diyview.DiyViewActivity;

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
                PhotoUploadActivity.startAction(this);
                break;
            case 1:
                StatusBarActivity.startAction(this);
                break;
            case 2:
                startActivity(new Intent(this,GreenDaoActivity.class));
                break;
            case 3:
                DiyViewActivity.startAction(this);
                break;
            case 4:
                ArouterDemoActivity.startAction(this);
                break;
            case 5:
                RecyclerViewDemoActivity.startAction(this);
                break;
        }
    }
}
