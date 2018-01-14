package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blmf.R;
import com.blmf.mvp.contract.ChangePhoneContract;
import com.blmf.mvp.model.ChangePhoneModel;
import com.blmf.mvp.model.entity.RegisterEntity;
import com.blmf.mvp.presenter.ChangePhonePresenter;
import com.blmf.mvp.ui.activity.ForgetActivity;

import blmf.lib_commen.base.BaseFragment;
import blmf.lib_commen.utils.CountDownTimerUtils;
import blmf.lib_commen.utils.ToastUtils;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class ForgetpwdFragment1 extends BaseFragment {

    public static ForgetpwdFragment1 newInstance() {
        Bundle args = new Bundle();
        ForgetpwdFragment1 fragment = new ForgetpwdFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void back(View v) {
        goBack();
    }

    public void startNextFragment(View v) {
        if (mActivity instanceof onFragmentItemClickListener) {
            ((MainActivity) mActivity).goNext("123456789");
        }
    }

    public interface onFragmentItemClickListener {
        void goNext(String phone);
    }
}
