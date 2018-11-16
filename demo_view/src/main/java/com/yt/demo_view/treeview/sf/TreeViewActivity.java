package com.yt.demo_view.treeview.sf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.yt.demo_view.R;

public class TreeViewActivity extends AppCompatActivity {

    private AndroidTreeView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view);
        LinearLayout containerView = (LinearLayout) findViewById(R.id.ll);

        //创建树根节点——不会显示，从第一个节点开始显示
        TreeNode root = TreeNode.root();

        //1
        TreeNode p1 = new TreeNode(new MyHolder.IconTreeItem(R.string.ic_folder,"parent1"));
        TreeNode p2 = new TreeNode(new MyHolder.IconTreeItem(R.string.ic_folder,"parent2"));
        TreeNode p3 = new TreeNode(new MyHolder.IconTreeItem(R.string.ic_folder,"parent3"));
        //添加子节点
        fillFolder(p1);
        fillFolder(p2);
        fillFolder(p3);

        //将第一个节点添加进根节点
        root.addChildren(p1, p2, p3);

        //将树形结构布局视图添加到布局中
        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        //给所有节点设置点击事件
        tView.setDefaultNodeClickListener(nodeClickListener);
        tView.setDefaultViewHolder(MyHolder.class);
        containerView.addView(tView.getView());

        //第二个节点设置点击事件
        p2.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Toast.makeText(TreeViewActivity.this, "第二个节点", Toast.LENGTH_SHORT).show();
            }
        });
        //全部展开
//        tView.expandAll();
        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }
    }

    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 0; i < 5; i++) {
            TreeNode file = new TreeNode(new MyHolder.IconTreeItem(R.string.ic_folder, "parent3"));
            currentNode.addChild(file);
            currentNode = file;
        }
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
//            node.
            MyHolder.IconTreeItem item = (MyHolder.IconTreeItem) value;
            Toast.makeText(TreeViewActivity.this, "Long click: " + item.text, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }
}
