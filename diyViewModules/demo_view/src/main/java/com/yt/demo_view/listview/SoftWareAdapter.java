package com.yt.demo_view.listview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yt.demo_view.R;

import java.util.List;

/**
 * Created by GOG on 2016/11/26.
 */

public class SoftWareAdapter extends BaseAdapter {
    private List<AppInfo> userApp ;
    private List<AppInfo> systemApp;
    private Context context;

    public SoftWareAdapter(Context context, List<AppInfo> systemApp, List<AppInfo> userApp) {
        this.context = context;
        this.systemApp = systemApp;
        this.userApp = userApp;
    }

    @Override
    public int getCount() {
        return userApp.size() + systemApp.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //拿到指定position的条目类型
    @Override
    public int getItemViewType(int position) {
        //标题布局类型
        if (position == 0 || position == userApp.size() + 1) {
            return 0;
        }
        //条目布局
        return 1;
    }

    //设置条目类型个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //调用getItemViewType方法，根据条目的显示的样式类型，设置条目显示相应的样式
        if (getItemViewType(position) == 0) {
            TextView textView = new TextView(context);
            textView.setTextSize(18);
            textView.setPadding(8, 8, 8, 8);
            textView.setBackgroundColor(Color.BLUE);
            //标题布局类型
            if (position == 0) {
                textView.setText("用户程序(" + userApp.size() + ")");
            } else {
                textView.setText("系统程序(" + systemApp.size() + ")");
            }
            return textView;
        } else {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_software, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            //判断当前位置
            AppInfo info;
            if (position <= userApp.size()) {
                info = userApp.get(position - 1);
            } else {
                info = systemApp.get(position - userApp.size() - 2);
            }
            viewHolder.icon.setImageDrawable(info.icon);
            viewHolder.name.setText(info.name);
            viewHolder.size.setText(android.text.format.Formatter.formatFileSize(context, info.size));
            viewHolder.isSystem.setText(info.isSD ? "SD" : "手机内存");
            return convertView;
        }
    }

    static class ViewHolder {
        public TextView name;
        public TextView isSystem;
        public TextView size;
        public ImageView icon;

        public ViewHolder(View item) {
            this.name = (TextView) item.findViewById(R.id.tv_item_software_name);
            this.icon = (ImageView) item.findViewById(R.id.iv_item_software);
            this.size = (TextView) item.findViewById(R.id.tv_item_software_size);
            this.isSystem = (TextView) item.findViewById(R.id.tv_item_software_issystem);
        }
    }
}
