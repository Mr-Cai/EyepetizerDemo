package open.moive.ui

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_watch.*
import open.moive.R
import open.moive.adapter.DownloadAdapter
import open.moive.mvp.model.bean.VideoBean
import open.moive.utils.ObjectSaveUtils
import open.moive.utils.SPUtils
import zlc.season.rxdownload2.RxDownload

class CacheActivity : AppCompatActivity() {
    var mList = ArrayList<VideoBean>()
    lateinit var mAdapter: DownloadAdapter
    var mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            var list = msg?.data?.getParcelableArrayList<open.moive.mvp.model.bean.VideoBean>("beans")
            if (list?.size?.compareTo(0) == 0) {
                tv_hint.visibility = View.VISIBLE
            } else {
                tv_hint.visibility = View.GONE
                if (mList.size > 0) {
                    mList.clear()
                }
                list?.let { mList.addAll(it) }
                mAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setToolbar()
        DataAsyncTask(mHandler, this).execute()
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = open.moive.adapter.DownloadAdapter(this, mList)
        mAdapter.setOnLongClickListener(object : open.moive.adapter.DownloadAdapter.OnLongClickListener {
            override fun onLongClick(position: Int) {
                addDialog(position)
            }
        })

        recyclerView.adapter = mAdapter
    }

    private fun addDialog(position: Int) {
        var builder = AlertDialog.Builder(this)
        var dialog = builder.create()
        builder.setMessage("是否删除当前视频")
        builder.setNegativeButton("否", { dialog, which ->
            dialog.dismiss()
        })
        builder.setPositiveButton("是", { dialog, which ->
            deleteDownload(position)
        })
        builder.show()
    }

    private fun deleteDownload(position: Int) {
        RxDownload.getInstance(this@CacheActivity).deleteServiceDownload(mList[position].playUrl, true).subscribe()
        SPUtils.getInstance(this, "downloads").put(mList[position].playUrl.toString(), "")
        var count = position + 1
        ObjectSaveUtils.deleteFile("download$count", this)
        mList.removeAt(position)
        mAdapter.notifyItemRemoved(position)
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar!!
        bar.title = "我的缓存"
        bar.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private class DataAsyncTask(handler: Handler, activity: CacheActivity) : AsyncTask<Void, Void, ArrayList<open.moive.mvp.model.bean.VideoBean>>() {
        var activity: CacheActivity = activity
        var handler = handler
        override fun doInBackground(vararg params: Void?): ArrayList<open.moive.mvp.model.bean.VideoBean>? {
            var list = ArrayList<open.moive.mvp.model.bean.VideoBean>()
            var count: Int = SPUtils.getInstance(activity, "downloads").getInt("count")
            var i = 1
            while (i.compareTo(count) <= 0) {
                var bean: open.moive.mvp.model.bean.VideoBean
                if (ObjectSaveUtils.getValue(activity, "download$i") == null) {
                    continue
                } else {
                    bean = ObjectSaveUtils.getValue(activity, "download$i") as VideoBean
                }
                list.add(bean)
                i++
            }
            return list
        }

        override fun onPostExecute(result: ArrayList<open.moive.mvp.model.bean.VideoBean>?) {
            super.onPostExecute(result)
            var message = handler.obtainMessage()
            var bundle = Bundle()
            bundle.putParcelableArrayList("beans", result)
            message.data = bundle
            handler.sendMessage(message)
        }

    }

}