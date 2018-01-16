package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by ${zhangyuanchao} on 2017/12/18.
 */

public class ForgetpwdFragment2 extends BaseFragment{


    public static ForgetpwdFragment2 newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone",phone);
        ForgetpwdFragment2 fragment = new ForgetpwdFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void onViewClicked(View view) {
        if (!(mActivity instanceof onForgetFragment2ItemClick)) return;
        switch (view.getId()) {
//            case R.id.iv_top_left:
//                FragmentUtils.pop(getFragmentManager());
////                ((ForgetActivity) mActivity).onBackItemCLickListener();
//                break;
//            case R.id.btn_submit:
//                String Newpwd1 = etNewpwd.getText().toString().trim();
//                String NewpwdConfig = etNewpwdConfig.getText().toString().trim();
//                if(TextUtils.isEmpty(Newpwd1)||TextUtils.isEmpty(NewpwdConfig)){
//                    showToast("请输入完整信息");
//                    return;
//                }
//                mPresenter.forgetPasswordRequest(phone,Newpwd1,NewpwdConfig);
//                break;
        }
    }

    public interface onForgetFragment2ItemClick {
        void onSubmitItemCLickListener();

        void onBackItemCLickListener();
    }
}
