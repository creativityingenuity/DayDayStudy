package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Fragment2 extends BaseFragment{


    public static Fragment2 newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString("phone",phone);
        Fragment2 fragment = new Fragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Button back = rootView.findViewById(R.id.btn_back);
        Button start = rootView.findViewById(R.id.btn_start);
        final EditText text = rootView.findViewById(R.id.et_input);

        //打开下一个页面
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNameSetOk(text.getText().toString().trim());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    public void setOnFragment2ItemClickCallBack(OnFragment2ItemClickListener clickCallBack) {
        this.listener = clickCallBack;
    }

    private OnFragment2ItemClickListener listener;

    public interface OnFragment2ItemClickListener {
        void onNameSetOk(String name);

        void onBackItemCLickListener();
    }
}
