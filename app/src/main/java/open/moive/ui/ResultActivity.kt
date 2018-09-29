package open.moive.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_result.*
import open.moive.R
import open.moive.adapter.FeedAdapter
import open.moive.mvp.contract.ResultContract
import open.moive.mvp.model.bean.HotBean
import open.moive.mvp.presenter.ResultPresenter

/**
 * Created by lvruheng on 2017/7/11.
 */
class ResultActivity : AppCompatActivity(), ResultContract.View, SwipeRefreshLayout.OnRefreshListener {
    lateinit var keyWord: String
    lateinit var mPresenter: ResultPresenter
    lateinit var mAdapter: FeedAdapter
    var mIsRefresh: Boolean = false
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
        mAdapter = open.moive.adapter.FeedAdapter(this, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    start = start.plus(10)
                    mPresenter.requestData(keyWord, start)
                }
            }
        })
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar!!
        bar.title = "'$keyWord' 相关"
        bar.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(bean: open.moive.mvp.model.bean.HotBean) {
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