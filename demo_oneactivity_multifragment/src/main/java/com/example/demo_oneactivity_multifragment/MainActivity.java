package com.example.demo_oneactivity_multifragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * 一个activity 多个fragment结构示例
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(null!=data) {
            User user = (User) data.getSerializableExtra("user");
            Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        startActivityForResult(new Intent(this, LoginActivity.class), 1);
    }
}
