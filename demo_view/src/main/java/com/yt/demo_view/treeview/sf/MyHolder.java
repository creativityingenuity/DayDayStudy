package com.yt.demo_view.treeview.sf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.yt.demo_view.R;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/7.
 * 自定义节点视图
 */

public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {

    public MyHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_profile_node, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);

        tvValue.setText(value.text);
        return view;
    }

    public static class IconTreeItem {
        public String text;

        public IconTreeItem( String text) {
            this.text = text;
        }
    }
}
