package com.yt.daydaystudy.demo_fragmentdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yt.daydaystudy.R;


/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/27.
 * 优点：
 * 当activity发生重建的情况下，DialogFragment能够自动重建，恢复原来的状态，并且能够它能正确处理生命周期事件
 */

public class DemoDialog extends DialogFragment implements View.OnClickListener {
    private static final String ARGS_ALERT = "alert";
    private Context context;
    public static DemoDialog newInstance(String alert) {
        DemoDialog f = new DemoDialog();
        Bundle args = new Bundle();
        args.putString(ARGS_ALERT, alert);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框是否可以取消
        setCancelable(false);
        //设置dialog显示风格
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String alert = getArguments().getString(ARGS_ALERT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        ImageView mIvClose = view.findViewById(R.id.login_iv);
        TextView mTvRegister = view.findViewById(R.id.login_register);
        EditText mEtUsername = view.findViewById(R.id.login_et1);
        Button mBtnLogin = view.findViewById(R.id.login_btn);
        EditText mEtPassword = view.findViewById(R.id.login_et2);
        mIvClose.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                break;
        }
    }

    /**
     * 建造者模式
     */
    public static class Builder {
        /**
         * 各种属性
         */
        private Context context;
        private String left;
        private String right;
        private View.OnClickListener leftOCL;
        private View.OnClickListener rightOCL;
        private Button leftButton;
        private Button rightButton;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 各种组装配置方式
         */
        public Builder setLeftText(String value) {
            this.left = value;
            return this;
        }

        public Builder setRightText(String value) {
            this.right = value;
            return this;
        }

        public Builder setLeftOnClick(View.OnClickListener left) {
            this.leftOCL = left;
            return this;
        }

        public Builder setRightOnClick(View.OnClickListener right) {
            this.rightOCL = right;
            return this;
        }

        /**
         * 最后调用生成Dialog对象
         *
         * @return
         */
        public DemoDialog create() {
            DemoDialog dialog = new DemoDialog();

            //            View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
            //            leftButton=(Button)view.findViewById(R.id.button2);
            //            rightButton=(Button)view.findViewById(R.id.quxiao_btn);
            //
            //            if (!TextUtils.isEmpty(left))
            //                leftButton.setText(left);
            //            if (!TextUtils.isEmpty(right))
            //                rightButton.setText(right);
            //            if (leftOCL!=null)
            //                leftButton.setOnClickListener(leftOCL);
            //            if (rightOCL!=null)
            //                rightButton.setOnClickListener(rightOCL);



            return dialog;

        }
    }
}
