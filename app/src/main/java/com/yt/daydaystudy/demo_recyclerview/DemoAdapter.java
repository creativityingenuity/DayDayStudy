package com.yt.daydaystudy.demo_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yt.daydaystudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/10/25.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {
    private List<Boolean> isClicks;
    private List<String> mData;

    public DemoAdapter(List<String> data) {
        mData = data;
        isClicks = new ArrayList<>();
        for(int i = 0;i<mData.size();i++){
            isClicks.add(false);
        }
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(final DemoViewHolder holder, int position) {
        /**
         * 数据处理和视图加载分离
         * 优化1. 从后台拉取数据后，若是数据需要进行二次解析 那么就放在网络请求子线程中去做
         * 绝对不能再vh设置视图数据时 进行解析
         * eg:
         * mTextView.setText(   Html.fromHtml(data).toString()   );
         */
        String s = mData.get(position);
        holder.tv.setText(s);
        //设置点击选择器
        if(isClicks.get(position)){
//            viewHolder.back.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }else{
        }

        if(mOnItemClickListener!=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
    }
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public DemoViewHolder(View itemView) {
            super(itemView);
             tv = itemView.findViewById(R.id.tv);
        }
    }
}
