package com.yt.daydaystudy.test_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yt.daydaystudy.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NetworkStateView.OnRefreshListener {

    private NetworkStateView networkStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
    }
    private void initView() {
        networkStateView = (NetworkStateView) findViewById(R.id.nsv_state_view);
        Button bt_error = (Button) findViewById(R.id.bt_error);
        bt_error.setOnClickListener(this);
        networkStateView.setOnRefreshListener(this);
        networkStateView.showLoading();
        showSuccess();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_error:
                networkStateView.showError();
                break;
        }
        showSuccess();
    }

    @Override
    public void onRefresh() {
        networkStateView.showLoading();
        showSuccess();
    }

    private void showSuccess() {
        networkStateView.postDelayed(new Runnable() {
            @Override
            public void run() {
                networkStateView.showSuccess();
            }
        }, 2000);
    }
}
