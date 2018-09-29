package open.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_result.*
import open.movie.R
import open.movie.adapter.FeedAdapter
import open.movie.mvp.contract.ResultContract
import open.movie.mvp.model.bean.HotBean
import open.movie.mvp.presenter.ResultPresenter

class ResultActivity : AppCompatActivity(), ResultContract.View,
        SwipeRefreshLayout.OnRefreshListener {
    lateinit var keyWord: String
    lateinit var mPresenter: ResultPresenter
    private lateinit var mAdapter: FeedAdapter
    private var mIsRefresh: Boolean = false
    var mList = ArrayList<HotBean.ItemListBean.DataBean>()
    var start: Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setContentView(R.layout.activity_result)
        keyWord = intent.getStringExtra("keyWord")
        mPresenter = ResultPresenter(this, this)
        mPresenter.requestData(keyWord, start)
        setToolbar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = open.movie.adapter.FeedAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList.size - 1) {
                    start = start.plus(10)
                    mPresenter.requestData(keyWord, start)
                }
            }
        })
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        val bar = supportActionBar!!
        bar.title = "'$keyWord' 相关"
        bar.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(bean: open.movie.mvp.model.bean.HotBean) {
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        mAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            start = 10
            mPresenter.requestData(keyWord, start)
        }
    }
}