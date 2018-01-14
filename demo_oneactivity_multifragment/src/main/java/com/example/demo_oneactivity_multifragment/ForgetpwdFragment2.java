package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blmf.R;
import com.blmf.mvp.contract.ForgetpwdContract;
import com.blmf.mvp.model.ForgetpwdModel;
import com.blmf.mvp.presenter.ForgetpwdPresenter;

import blmf.lib_commen.base.BaseFragment;
import blmf.lib_commen.utils.FragmentUtils;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class ForgetpwdFragment2 extends BaseFragment<ForgetpwdPresenter,ForgetpwdModel> implements ForgetpwdContract.View{
    @Bind(R.id.iv_top_left)
    ImageView ivTopLeft;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_newpwd)
    EditText etNewpwd;
    @Bind(R.id.et_newpwd_config)
    EditText etNewpwdConfig;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private String phone;

    public static ForgetpwdFragment2 newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone",phone);
        ForgetpwdFragment2 fragment = new ForgetpwdFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd_two;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("登录密码修改");
        ivTopLeft.setVisibility(View.VISIBLE);
        phone = getArguments().getString("phone");
    }

    @OnClick({R.id.iv_top_left, R.id.btn_submit})
    public void onViewClicked(View view) {
        if (!(mActivity instanceof onForgetFragment2ItemClick)) return;
        switch (view.getId()) {
            case R.id.iv_top_left:
                FragmentUtils.pop(getFragmentManager());
//                ((ForgetActivity) mActivity).onBackItemCLickListener();
                break;
            case R.id.btn_submit:
                String Newpwd1 = etNewpwd.getText().toString().trim();
                String NewpwdConfig = etNewpwdConfig.getText().toString().trim();
                if(TextUtils.isEmpty(Newpwd1)||TextUtils.isEmpty(NewpwdConfig)){
                    showToast("请输入完整信息");
                    return;
                }
                mPresenter.forgetPasswordRequest(phone,Newpwd1,NewpwdConfig);
                break;
        }
    }

    @Override
    public void returnforgetPassword(String msg) {
        showToast(msg);
        mActivity.finish();
    }

    public interface onForgetFragment2ItemClick {
        void onSubmitItemCLickListener();

        void onBackItemCLickListener();
    }
}
