package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blmf.R;
import com.blmf.mvp.contract.RepertoryContract;
import com.blmf.mvp.model.RepertoryModel;
import com.blmf.mvp.model.entity.RecordListEntity;
import com.blmf.mvp.model.entity.ReperToryEntity;
import com.blmf.mvp.model.event.ReperToryEvent;
import com.blmf.mvp.presenter.RepertoryPresenter;
import com.blmf.mvp.ui.activity.RepertoryActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import blmf.lib_commen.base.BaseFragment;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ${zhangyuanchao} on 2017/12/15.
 * 收益明细
 */

public class RepertoryFragment1 extends BaseFragment<RepertoryPresenter, RepertoryModel> implements RepertoryContract.View{
    @Bind(R.id.iv_top_left)
    ImageView iv_top_left;//返回
    @Bind(R.id.tv_title)
    TextView tv_title;//标题
    @Bind(R.id.tv_top_right)
    TextView tvTopRight;
    @Bind(R.id.repertory_tv_ye)
    TextView repertoryTvYe;
    @Bind(R.id.bt_jinhuo)
    Button btJinhuo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repertory;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tv_title.setText("我的库存");
        iv_top_left.setVisibility(View.VISIBLE);
        tvTopRight.setVisibility(View.VISIBLE);
        tvTopRight.setText("进货记录");
        mPresenter.repertoryInfoRequst();
    }

    @Override
    public void returnRepertoryInfo(ReperToryEntity data) {
        repertoryTvYe.setText(data.getRepertory());
        EventBus.getDefault().post(new ReperToryEvent(data.getBalance()));
    }

    @Override
    public void returnrecordList(List<RecordListEntity> data) {

    }

    public interface OnFragmentItemCLickLIstener {
        void onStockClickListener();

        void onRecordClickListener();
    }

    @OnClick({R.id.tv_top_right, R.id.bt_jinhuo, R.id.iv_top_left})
    public void onViewClicked(View view) {
        if (!(mActivity instanceof OnFragmentItemCLickLIstener)) return;
        switch (view.getId()) {
            case R.id.tv_top_right:
                ((RepertoryActivity) mActivity).onRecordClickListener();
                break;
            case R.id.bt_jinhuo:
                ((RepertoryActivity) mActivity).onStockClickListener();
                break;
            case R.id.iv_top_left:
                finsh();
                break;
        }
    }
}
