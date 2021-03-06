package cn.yt.yttreeview.adapter;

import cn.yt.yttreeview.anno.TreeNodeId;
import cn.yt.yttreeview.anno.TreeNodeLabel;
import cn.yt.yttreeview.anno.TreeNodePid;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/11/16.
 */

public class FileBean {
    @TreeNodeId
    private int _id;
    @TreeNodePid
    private int parentId;
    @TreeNodeLabel
    private String name;
    private long length;
    private String desc;

    public FileBean(int _id, int parentId, String name) {
        super();
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
    }

}
