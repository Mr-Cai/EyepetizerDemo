package open.movie.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import open.movie.R
import open.movie.mvp.model.bean.VideoBean
import open.movie.utils.ImageLoadUtils
import open.movie.utils.ObjectSaveUtils
import open.movie.utils.SPUtils
import open.movie.utils.showToast
import zlc.season.rxdownload2.RxDownload
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.concurrent.ExecutionException

class VideoDetailActivity : AppCompatActivity() {
    companion object {
        var MSG_IMAGE_LOADED = 101
    }

    private var mContext: Context = this
    private lateinit var imageView: ImageView
    lateinit var bean: VideoBean
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private lateinit var orientationUtils: OrientationUtils

    private var mHandler = Handler(Handler.Callback { msg ->
        when (msg?.what) {
            MSG_IMAGE_LOADED -> {
                Log.e("video", "setImage")
                gsy_player.thumbImageView = imageView
            }
        }
        false
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        bean = intent.getParcelableExtra("data")
        initView()
        prepareVideo()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val bgUrl = bean.blurred
        bgUrl.let { ImageLoadUtils.displayHigh(this, iv_bottom_bg, bgUrl!!) }
        tv_video_desc.text = bean.description
        tv_video_desc.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_video_title.text = bean.title
        tv_video_title.typeface = Typeface.createFromAsset(this.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        val category = bean.category
        val duration = bean.duration
        val minute = duration!!.div(60)
        val second = duration.minus((minute.times(60)))
        val realMinute: String
        val realSecond: String
        realMinute = (if (minute < 10) {
            "0$minute"
        } else minute.toString())
        realSecond = (if (second >= 10) {
            second.toString()
        } else "0$second")
        tv_video_time.text = "$category / $realMinute'$realSecond''"
        tv_video_favor.text = bean.collect.toString()
        tv_video_share.text = bean.share.toString()
        tv_video_reply.text = bean.share.toString()
        tv_video_download.setOnClickListener {
            //点击下载
            val url = bean.playUrl.let { it1 -> SPUtils.getInstance(this, "downloads").getString(it1!!) }
            if (url == "") {
                var count = SPUtils.getInstance(this, "downloads").getInt("count")
                count = (if (count != -1) count.inc() else 1)
                SPUtils.getInstance(this, "downloads").put("count", count)
                ObjectSaveUtils.saveObject(this, "download$count", bean)
                addMission(bean.playUrl, count)
            } else {
                showToast("该视频已经缓存过了")
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun addMission(playUrl: String?, count: Int) {
        RxDownload.getInstance(this).serviceDownload(playUrl, "download$count").subscribe({
            showToast("开始下载")
            SPUtils.getInstance(this, "downloads").put(bean.playUrl.toString(), bean.playUrl.toString())
            SPUtils.getInstance(this, "download_state").put(playUrl.toString(), true)
        }, {
            showToast("添加任务失败")
        })
    }

    private fun prepareVideo() {
        val uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            Log.e("uri", uri)
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(bean.playUrl, false, null, null)
        }
        //增加封面
        imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageViewAsyncTask(mHandler, this, imageView).execute(bean.feed)
        gsy_player.titleTextView.visibility = View.GONE
        gsy_player.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true)
        //关闭自动旋转
        gsy_player.isRotateViewAuto = false
        gsy_player.isLockLand = false
        gsy_player.isShowFullAnimation = false
        gsy_player.isNeedLockFull = true
        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(mContext, true, true)
        }
        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener {
            onBackPressed()
        }

    }

    private class ImageViewAsyncTask(private var handler: Handler, activity: VideoDetailActivity, private val mImageView: ImageView) : AsyncTask<String, Void, String>() {
        private var mPath: String? = null
        private var mIs: FileInputStream? = null
        private var mActivity: VideoDetailActivity = activity
        override fun doInBackground(vararg params: String): String? {
            val future = Glide.with(mActivity)
                    .load(params[0])
                    .downloadOnly(100, 100)
            try {
                val cacheFile = future.get()
                mPath = cacheFile.absolutePath
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }

            return mPath
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            try {
                mIs = FileInputStream(s)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val bitmap = BitmapFactory.decodeStream(mIs)
            mImageView.setImageBitmap(bitmap)
            val message = handler.obtainMessage()
            message.what = MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

    override fun onBackPressed() {
        orientationUtils.let {
            orientationUtils.backToProtVideo()
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        orientationUtils.let {
            orientationUtils.releaseListener()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsy_player.isIfCurrentIsFullscreen) {
                    gsy_player.startWindowFullscreen(mContext, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (gsy_player.isIfCurrentIsFullscreen) {
                    //   StandardGSYVideoPlayer.backFromWindowFull(this)
                }
                orientationUtils.let { orientationUtils.isEnable = true }
            }
        }
    }
}
