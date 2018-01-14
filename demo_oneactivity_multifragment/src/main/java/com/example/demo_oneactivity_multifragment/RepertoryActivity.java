package com.example.demo_oneactivity_multifragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.blmf.R;
import com.blmf.mvp.ui.fragment.repertory.RecordFragment;
import com.blmf.mvp.ui.fragment.repertory.RepertoryFragment1;
import com.blmf.mvp.ui.fragment.repertory.RepertoryFragment2;
import com.blmf.mvp.ui.fragment.wallet.MyWalletFragment;

import blmf.lib_commen.base.BaseActivity;


public class RepertoryActivity extends BaseActivity implements RepertoryFragment1.OnFragmentItemCLickLIstener {
    private RepertoryFragment1 fragment1;
    private FragmentTransaction mTransaction;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mywallet;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragment1 = new RepertoryFragment1();
            mTransaction = getSupportFragmentManager().beginTransaction();
            mTransaction.add(R.id.container, fragment1, MyWalletFragment.class.getSimpleName());
            mTransaction.commit();
        }
    }

    @Override
    public void onStockClickListener() {
        RepertoryFragment2 fragment2 = new RepertoryFragment2();
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.hide(fragment1);
        mTransaction.add(R.id.container, fragment2, fragment2.getClass().getSimpleName());
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

    @Override
    public void onRecordClickListener() {
        RecordFragment recordFragment = new RecordFragment();
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.hide(fragment1);
        mTransaction.add(R.id.container, recordFragment, recordFragment.getClass().getSimpleName());
        mTransaction.addToBackStack(null);
        mTransaction.commit();
    }

}
