package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;


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
//            ((MainActivity) mActivity).goNext("123456789");
        }
    }

    public interface onFragmentItemClickListener {
        void goNext(String phone);
    }
}
