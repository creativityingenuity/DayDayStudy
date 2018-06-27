package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 每个fragment的责任：
 * 收集信息，单一原则，完成业务逻辑
 * 并把结果返回给宿主activity
 * fragment并不负责启动下一个步骤，达到了解耦的效果
 */
public class Fragment1 extends BaseFragment {

    public static Fragment1 newInstance() {
        Bundle args = new Bundle();
        Fragment1 fragment = new Fragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
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
                listener.onPhoneSetOk(text.getText().toString().trim());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    public void setOnFragment1ItemClickCallBack(OnFragment1ItemClickListener clickCallBack) {
        this.listener = clickCallBack;
    }

    private OnFragment1ItemClickListener listener;

    public interface OnFragment1ItemClickListener {
        void onPhoneSetOk(String phone);
    }
}
