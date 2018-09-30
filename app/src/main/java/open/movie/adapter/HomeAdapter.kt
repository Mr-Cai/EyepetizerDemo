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
import kotlinx.android.synthetic.main.item_home.view.*
import open.movie.R
import open.movie.utils.mvp.HomeBean
import open.movie.utils.mvp.VideoBean
import open.movie.ui.VideoDetailActivity
import open.movie.utils.ImageLoadUtils
import open.movie.utils.ObjectSaveUtils
import open.movie.utils.SPUtils

class HomeAdapter(
        context: Context,
        list: MutableList<HomeBean.IssueListBean.ItemListBean>?
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var context: Context? = null
    var list: MutableList<HomeBean.IssueListBean.ItemListBean>
    private var inflater: LayoutInflater

    init {
        this.context = context
        this.list = list!!
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HomeAdapter.HomeViewHolder(inflater.inflate(R.layout.item_home, parent, false), context!!)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val bean = list[position]
        val title = bean.data.title
        val category = bean.data.category
        val minute = bean.data.duration?.div(60)
        val second = bean.data.duration?.minus((minute?.times(60)) as Long)
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
        val photo = bean.data.cover?.feed
        val author = bean.data.author
        ImageLoadUtils.display(context!!, holder.photoPic, photo as String)
        holder.titleTxT.text = title
        holder.detailTxT.text = "发布于$category/$realMinute:$realSecond"
        if (author != null) {
            ImageLoadUtils.display(context!!, holder.userPic, author.icon)
        } else {
            holder.userPic?.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            //跳转视频详情页
            val intent = Intent(context, VideoDetailActivity::class.java)
            val desc = bean.data.description
            val duration = bean.data.duration
            val playUrl = bean.data.playUrl
            val blurred = bean.data.cover?.blurred
            val collect = bean.data.consumption?.collectionCount
            val share = bean.data.consumption?.shareCount
            val reply = bean.data.consumption?.replyCount
            val time = System.currentTimeMillis()
            val videoBean = VideoBean(
                    photo,
                    title,
                    desc,
                    duration,
                    playUrl,
                    category,
                    blurred,
                    collect,
                    share,
                    reply,
                    time
            )
            val url = SPUtils.getInstance(context!!, "beans").getString(playUrl)
            if (url == "") {
                var count = SPUtils.getInstance(context!!, "beans").getInt("count")
                count = (if (count != -1) count.inc() else 1)
                SPUtils.getInstance(context!!, "beans").put("count", count)
                SPUtils.getInstance(context!!, "beans").put(playUrl, playUrl)
                ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)
            }
            intent.putExtra("data", videoBean as Parcelable)
            context?.startActivity(intent)
        }
    }

    class HomeViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        var detailTxT: TextView = itemView.tv_detail
        var titleTxT: TextView = itemView.tv_title
        var photoPic: ImageView = itemView.iv_photo
        var userPic: ImageView? = itemView.iv_user

        init {
            titleTxT.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        }
    }

}
