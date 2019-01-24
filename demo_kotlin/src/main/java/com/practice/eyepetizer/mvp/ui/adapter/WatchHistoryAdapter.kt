package com.practice.eyepetizer.mvp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.yt.demo_kotlin.R
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.practice.eyepetizer.glide.GlideApp
import com.practice.eyepetizer.globle.Constants
import com.practice.eyepetizer.globle.durationFormat
import com.practice.eyepetizer.mvp.model.bean.HomeBean
import com.practice.eyepetizer.mvp.ui.activity.VideoDetailActivity

/**
 * Call:vipggxs@163.com
 * Created by YT on 2019/1/24.
 */
class WatchHistoryAdapter(mContext: Context, mData: ArrayList<HomeBean.Issue.Item>, mLayoutId: Int) : CommonAdapter<HomeBean.Issue.Item>(mContext, mData, mLayoutId) {

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        with(holder){
            setText(R.id.tv_title,data.data?.title!!)
            setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
            setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(mContext)
                            .load(path)
                            .placeholder(R.mipmap.placeholder_banner)
                            .transition(DrawableTransitionOptions().crossFade())
                            .into(iv)
                }
            })
        }
        holder.getView<TextView>(R.id.tv_title).setTextColor(ContextCompat.getColor(mContext,R.color.color_black))
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_video_small_card), data)
        })
    }

    /**
     * 跳转到视频页面进行播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}