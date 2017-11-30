package yt.mvpdemo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import yt.mvpdemo.R;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.home_til_name)
    TextInputLayout mTilName;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.til_pwd)
    TextInputLayout mTilPwd;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
