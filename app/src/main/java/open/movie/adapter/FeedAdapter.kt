package open.movie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import open.movie.R
import open.movie.ui.activity.VideoDetailActivity
import open.movie.utils.ImageLoadUtils
import open.movie.utils.ObjectSaveUtils
import open.movie.utils.SPUtils
import open.movie.utils.mvp.HotBean
import open.movie.utils.mvp.VideoBean
import java.text.SimpleDateFormat

class FeedAdapter(context: Context, list: ArrayList<HotBean.ItemListBean.DataBean>) :
        RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    var context: Context? = null
    var list: ArrayList<HotBean.ItemListBean.DataBean>? = null
    private var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.list = list
        this.inflater = LayoutInflater.from(context)
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.FeedViewHolder =
            FeedAdapter.FeedViewHolder(inflater?.inflate(R.layout.item_feed_result, parent, false), context!!)

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: open.movie.adapter.FeedAdapter.FeedViewHolder, position: Int) {
        val photoUrl: String? = list?.get(position)?.cover?.feed
        photoUrl?.let { ImageLoadUtils.display(context!!, holder.iv_photo, it) }
        val title: String? = list?.get(position)?.title
        holder.tv_title.text = title
        val category = list?.get(position)?.category
        val duration = list?.get(position)?.duration
        val minute = duration?.div(60)
        val second = duration?.minus((minute?.times(60)) as Long)
        val releaseTime = list?.get(position)?.releaseTime
        val smf = SimpleDateFormat("MM-dd")
        val date = smf.format(releaseTime)
        val realMinute: String
        val realSecond: String
        realMinute = if (minute!! < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        realSecond = if (second!! < 10) {
            "0$second"
        } else {
            second.toString()
        }
        holder.tv_time.text = "$category / $realMinute'$realSecond'' / $date"
        holder.itemView.setOnClickListener {
            //跳转视频详情页
            val intent: Intent = Intent(context, VideoDetailActivity::class.java)
            var desc = list?.get(position)?.description
            var playUrl = list?.get(position)?.playUrl
            var blurred = list?.get(position)?.cover?.blurred
            var collect = list?.get(position)?.consumption?.collectionCount
            var share = list?.get(position)?.consumption?.shareCount
            var reply = list?.get(position)?.consumption?.replyCount
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            var url = SPUtils.getInstance(context!!, "beans").getString(playUrl!!)
            if (url.equals("")) {
                var count = SPUtils.getInstance(context!!, "beans").getInt("count")
                if (count != -1) {
                    count = count.inc()
                } else {
                    count = 1
                }
                SPUtils.getInstance(context!!, "beans").put("count", count)
                SPUtils.getInstance(context!!, "beans").put(playUrl, playUrl)
                ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)
            }
            intent.putExtra("data", videoBean as Parcelable)
            context?.let { context -> context.startActivity(intent) }
        }
    }

    class FeedViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView!!) {
        var iv_photo: ImageView = itemView?.findViewById(R.id.iv_photo) as ImageView
        var tv_title: TextView = itemView?.findViewById(R.id.tv_title) as TextView
        var tv_time: TextView = itemView?.findViewById(R.id.tv_detail) as TextView

        init {
            tv_title.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")

        }
    }
}