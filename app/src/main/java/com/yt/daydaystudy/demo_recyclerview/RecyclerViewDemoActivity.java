package com.yt.daydaystudy.demo_recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yt.daydaystudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Call:vipggxs@163.com
 * Created by YT on 2018/10/25.
 */

/**
 * 数据优化
 * 优化2.分页拉取数据，并对数据进行缓存？，提升二次加载速度，对于新增或者删除数据通过DiffUtil？ 来进行局部刷新数据，而不是一味地全局刷新数据。
 *
 *
 * 布局优化
 *减少过渡绘制
    3.减少布局层级，
        可以考虑使用自定义 View 来减少层级，或者更合理地设置布局来减少层级，不推荐在 RecyclerView 中使用 ConstraintLayout
    4.减少 xml 文件 inflate 时间
        这里的 xml 文件不仅包括 layout 的 xml，还包括 drawable 的 xml，xml 文件 inflate 出 ItemView 是通过耗时的 IO 操作，尤其当 Item 的复用几率很低的情况下，随着 Type 的增多，这种 inflate 带来的损耗是相当大的，此时我们可以用代码去生成布局，即 new View() 的方式，只要搞清楚 xml 中每个节点的属性对应的 API 即可。
 减少 View 对象的创建
 一个稍微复杂的 Item 会包含大量的 View，而大量的 View 的创建也会消耗大量时间，所以要尽可能简化 ItemView；设计 ItemType 时，对多 ViewType 能够共用的部分尽量设计成自定义 View，减少 View 的构造和嵌套。
 其他
 其他并不代表不重要，而是我不能把他们进行分类哈，其中可能某些操作会对你的 RecyclerView 有很大的优化。


 升级 RecycleView 版本到 25.1.0 及以上使用 Prefetch 功能，可参考 RecyclerView 数据预取。


 如果 Item 高度是固定的话，可以使用 RecyclerView.setHasFixedSize(true); 来避免 requestLayout 浪费资源；


 设置 RecyclerView.addOnScrollListener(listener); 来对滑动过程中停止加载的操作。


 如果不要求动画，可以通过 ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false); 把默认动画关闭来提升效率。


 对 TextView 使用 String.toUpperCase 来替代 android:textAllCaps="true"。


 对 TextView 使用 StaticLayout 或者 DynamicLayout 的自定义 View 来代替它。


 通过重写 RecyclerView.onViewRecycled(holder) 来回收资源。


 通过 RecycleView.setItemViewCacheSize(size); 来加大 RecyclerView 的缓存，用空间换时间来提高滚动的流畅性。


 如果多个 RecycledView 的 Adapter 是一样的，比如嵌套的 RecyclerView 中存在一样的 Adapter，可以通过设置 RecyclerView.setRecycledViewPool(pool); 来共用一个 RecycledViewPool。


 对 ItemView 设置监听器，不要对每个 Item 都调用 addXxListener，应该大家公用一个 XxListener，根据 ID 来进行不同的操作，优化了对象的频繁创建带来的资源消耗。


 通过 getExtraLayoutSpace 来增加 RecyclerView 预留的额外空间（显示范围之外，应该额外缓存的空间），如下所示：
 new LinearLayoutManager(this) {
    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
    return size;
    }
    };
 */
public class RecyclerViewDemoActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity,RecyclerViewDemoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_demo);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        initData();
    }

    private void initData() {
        List<String> data = new ArrayList<>();
        for (int i = 0;i<10000;i++){
            data.add("测试"+i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new DemoAdapter(data));
    }

}
