package open.movie.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import open.movie.R
import open.movie.utils.mvp.VideoBean
import open.movie.ui.activity.VideoDetailActivity
import open.movie.utils.ImageLoadUtils
import open.movie.utils.SPUtils
import zlc.season.rxdownload2.RxDownload
import zlc.season.rxdownload2.entity.DownloadFlag

class DownloadAdapter(context: Context, list: ArrayList<VideoBean>) : RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    private lateinit var longListener: open.movie.adapter.DownloadAdapter.OnLongClickListener
    var context: Context? = null
    var list: ArrayList<VideoBean>? = null
    private var inflater: LayoutInflater? = null
    private var isDownload = false
    private var hasLoaded = false
    private lateinit var disposable: Disposable

    init {
        this.context = context
        this.list = list
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): open.movie.adapter.DownloadAdapter.DownloadViewHolder {
        return open.movie.adapter.DownloadAdapter.DownloadViewHolder(inflater?.inflate(R.layout.item_download, parent, false), context!!)


    }

    override fun onBindViewHolder(holder: open.movie.adapter.DownloadAdapter.DownloadViewHolder, position: Int) {
        var photoUrl: String? = list?.get(position)?.feed
        photoUrl?.let { ImageLoadUtils.display(context!!, holder.iv_photo, it) }
        var title: String? = list?.get(position)?.title
        holder.tv_title.text = title
        var category = list?.get(position)?.category
        var duration = list?.get(position)?.duration
        isDownload = SPUtils.getInstance(context!!, "download_state").getBoolean(list?.get(position)?.playUrl!!)
        getDownloadState(list?.get(position)?.playUrl, holder)
        if (isDownload) {
            holder.iv_download_state.setImageResource(R.drawable.icon_download_stop)
        } else {
            holder.iv_download_state.setImageResource(R.drawable.icon_download_start)
        }
        holder.iv_download_state.setOnClickListener {
            if (isDownload) {
                isDownload = false
                SPUtils.getInstance(context!!, "download_state").put(list?.get(position)?.playUrl!!, false)
                holder.iv_download_state.setImageResource(R.drawable.icon_download_start)
                RxDownload.getInstance(context).pauseServiceDownload(list?.get(position)?.playUrl).subscribe()
            } else {
                isDownload = true
                SPUtils.getInstance(context!!, "download_state").put(list?.get(position)?.playUrl!!, true)
                holder.iv_download_state.setImageResource(R.drawable.icon_download_stop)
                addMission(list?.get(position)?.playUrl, position + 1)
            }
        }
        holder.itemView.setOnClickListener {
            //跳转视频详情页
            var intent: Intent = Intent(context, VideoDetailActivity::class.java)
            var desc = list?.get(position)?.description
            var playUrl = list?.get(position)?.playUrl
            var blurred = list?.get(position)?.blurred
            var collect = list?.get(position)?.collect
            var share = list?.get(position)?.share
            var reply = list?.get(position)?.reply
            var time = System.currentTimeMillis()
            var videoBean = VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            var url = SPUtils.getInstance(context!!, "beans").getString(playUrl!!)
            intent.putExtra("data", videoBean as Parcelable)
            if (hasLoaded) {
                var files = RxDownload.getInstance(context).getRealFiles(playUrl)
                var uri = Uri.fromFile(files!![0])
                intent.putExtra("loaclFile", uri.toString())
            }

            context?.let { context -> context.startActivity(intent) }
        }
        holder.itemView.setOnLongClickListener {
            longListener.onLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    private fun getDownloadState(playUrl: String?, holder: open.movie.adapter.DownloadAdapter.DownloadViewHolder?) {
        disposable = RxDownload.getInstance(context).receiveDownloadStatus(playUrl)
                .subscribe { event ->
                    //当事件为Failed时, 才会有异常信息, 其余时候为null.
                    if (event.flag == DownloadFlag.FAILED) {
                        val throwable = event.error
                        Log.w("Error", throwable)
                    }
                    var downloadStatus = event.downloadStatus
                    var percent = downloadStatus.percentNumber
                    if (percent == 100L) {
                        if (!disposable.isDisposed && disposable != null) {
                            disposable.dispose()
                        }
                        hasLoaded = true
                        holder?.iv_download_state?.visibility = View.GONE
                        holder?.tv_detail?.text = "已缓存"
                        isDownload = false
                        SPUtils.getInstance(context!!, "download_state").put(playUrl.toString(), false)
                    } else {
                        if (holder?.iv_download_state?.visibility != View.VISIBLE) {
                            holder?.iv_download_state?.visibility = View.VISIBLE
                        }
                        if (isDownload) {
                            holder?.tv_detail?.text = "缓存中 / $percent%"
                        } else {
                            holder?.tv_detail?.text = "已暂停 / $percent%"
                        }


                    }
                }

    }

    @SuppressLint("CheckResult")
    private fun addMission(playUrl: String?, count: Int) {
        RxDownload.getInstance(context).serviceDownload(playUrl, "download$count").subscribe({
            Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(context, "添加任务失败", Toast.LENGTH_SHORT).show()
        })
    }

    class DownloadViewHolder(itemView: View?, context: Context) : RecyclerView.ViewHolder(itemView!!) {
        var iv_photo: ImageView = itemView?.findViewById(R.id.iv_photo) as ImageView
        var tv_title: TextView = itemView?.findViewById(R.id.tv_title) as TextView
        var tv_detail: TextView = itemView?.findViewById(R.id.tv_detail) as TextView
        var iv_download_state: ImageView = itemView?.findViewById(R.id.iv_download_state) as ImageView

        init {
            tv_title.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }
    }

    interface OnLongClickListener {
        fun onLongClick(position: Int)
    }

    fun setOnLongClickListener(onLongClickListener: open.movie.adapter.DownloadAdapter.OnLongClickListener) {
        longListener = onLongClickListener
    }
}