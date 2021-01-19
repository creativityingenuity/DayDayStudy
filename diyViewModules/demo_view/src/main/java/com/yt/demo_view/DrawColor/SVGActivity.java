package com.yt.demo_view.DrawColor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.color.apply.svg.MatrixPathView;

public class SVGActivity extends AppCompatActivity {
    private String TAG = SVGActivity.class.getSimpleName();

    private MatrixPathView mSVGView;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        mSVGView = findViewById(R.id.svgview);
        frameLayout = findViewById(R.id.autoView);
        // 渲染完成，在进行下一步操作
        mSVGView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            // TODO Auto-generated method stub
        });

        findViewById(R.id.click).setOnClickListener(v -> {
            if (mSVGView.getSelectedItemList() != null) {
                Log.e(TAG, "" + mSVGView.getSelectedItemList().size());
//                AutoSVGPathView autoSVGPathView = new AutoSVGPathView(SVGActivity.this, mSVGView.getSelectedItemList());
//                frameLayout.addView(autoSVGPathView);
            } else {
                Toast.makeText(SVGActivity.this, "先给图片上色哦", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
