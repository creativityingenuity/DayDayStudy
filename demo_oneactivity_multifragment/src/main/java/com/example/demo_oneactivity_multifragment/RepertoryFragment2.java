package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blmf.R;

import blmf.lib_commen.base.BaseFragment;
import butterknife.Bind;
import butterknife.OnClick;


public class RepertoryFragment2 extends BaseFragment {
    @Bind(R.id.iv_top_left)
    ImageView iv_top_left;//返回
    @Bind(R.id.tv_title)
    TextView tv_title;//标题


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repertory_pay;
    }
    public interface OnFragmentItemCLickLIstener{
        void onStockClickListener();
        void onRecordClickListener();
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        iv_top_left.setVisibility(View.VISIBLE);
        tv_title.setText("立即进货");
    }


    @OnClick({R.id.iv_top_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_left:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

}
