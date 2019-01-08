package com.yt.daydaystudy;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class MainGridViewAdapter extends BaseAdapter {
    private String[] tv;

    public MainGridViewAdapter(String[] strings) {
        tv = strings;
    }

    @Override
    public int getCount() {
        return tv.length;
    }

    @Override
    public Object getItem(int position) {
        return tv[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(parent.getContext(), R.layout.item_btn, null);
        TextView button = (TextView) convertView.findViewById(R.id.btn);
        button.setText(tv[position]);
        return convertView;
    }
}

