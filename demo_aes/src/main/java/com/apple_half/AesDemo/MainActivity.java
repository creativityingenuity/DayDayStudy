package com.apple_half.AesDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // 定义自己的秘钥、位移量
    public final static String Encryption_SecretKey = "A9e4/vnQTrKF6otAGbM6zGsulKEL7b3x";
    public final static String Encryption_IV = "9mg+!7ed8b36*w`X";

    // 提供要加密的数据
    private String data = "android 加密解密测试";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    private void initView() {
        TextView secreTv = (TextView) findViewById(R.id.secret_key);
        secreTv.setText("SecretKey: " + Encryption_SecretKey);

        TextView ivTv = (TextView) findViewById(R.id.iv);
        ivTv.setText("iv: " + Encryption_IV);

        final EditText dataTv = (EditText) findViewById(R.id.data);
        dataTv.setText(data);

        final TextView result1 = (TextView) findViewById(R.id.result1);
        final TextView result2 = (TextView) findViewById(R.id.result2);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("加密");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //加密
                    String encrypted = AesUtils.encrypt2(dataTv.getText().toString());
                    result1.setText(encrypted);

                    // 解密
                    String decrypted = AesUtils.decrypt(encrypted);
                    result2.setText("对加密后的结果，进行解密：\n" + decrypted);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
