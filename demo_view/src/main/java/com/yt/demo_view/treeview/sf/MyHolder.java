package com.yt.demo_view.treeview.sf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;
import com.yt.demo_view.R;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/7.
 */

public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {
    private PrintView arrowView;
    public MyHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_profile_node, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.node_value);

        final PrintView iconView = (PrintView) view.findViewById(R.id.icon);
        iconView.setIconText(context.getResources().getString(value.icon));

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);

        tvValue.setText(value.text);

        if(node.getLevel()==1){
            Log.e("yt","level1");
        }

        if(node.isLeaf()){
            arrowView.setVisibility(View.GONE);
            Log.e("yt","isLeaf");
        }
        return view;
    }

    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    public static class IconTreeItem {
        public String text;
        public int icon;

        public IconTreeItem(int icon, String text) {
            this.text = text;
            this.icon = icon;
        }
    }
}
