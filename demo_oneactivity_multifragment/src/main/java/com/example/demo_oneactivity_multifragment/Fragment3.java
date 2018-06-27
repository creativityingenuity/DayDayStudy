package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Fragment3 extends BaseFragment {


    public static Fragment3 newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone", phone);
        Fragment3 fragment = new Fragment3();
        fragment.setArguments(args);
        return fragment;
    }

    public void setParams(String phone, String name) {
        Bundle args = new Bundle();
        args.putString("phone", phone);
        args.putString("name", name);
        setArguments(args);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_three;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        final String phone = arguments.getString("phone");
        final String name = arguments.getString("name");
        Button back = rootView.findViewById(R.id.btn_back);
        Button start = rootView.findViewById(R.id.btn_start);
        final EditText text = rootView.findViewById(R.id.et_input);

        //打开下一个页面
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPwdSetOk(new User(name, text.getText().toString().trim(), phone));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    public void setOnFragment3ItemClickCallBack(OnFragment3ItemClickListener clickCallBack) {
        this.listener = clickCallBack;
    }

    private OnFragment3ItemClickListener listener;


    public interface OnFragment3ItemClickListener {
        void onPwdSetOk(User user);
    }
}
