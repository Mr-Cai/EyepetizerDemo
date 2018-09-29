package open.moive.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_fragment.*
import open.moive.mvp.model.bean.HomeBean.IssueListBean.ItemListBean
import java.util.*
import java.util.regex.Pattern
import open.moive.R
import open.moive.adapter.HomeAdatper
import open.moive.mvp.presenter.HomePresenter

/**
 * Created by lvruheng on 2017/7/4.
 */
class HomeFragment : BaseFragment(), open.moive.mvp.contract.HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    var mIsRefresh: Boolean = false
    var mPresenter: HomePresenter? = null
    var mList = ArrayList<ItemListBean>()
    var mAdapter: HomeAdatper? = null
    var data: String? = null
    override fun setData(bean: open.moive.mvp.model.bean.HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if (mList.size > 0) {
                mList.clear()
            }

        }
        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { mList.add(it) }
        mAdapter?.notifyDataSetChanged()
    }


    override fun getLayoutResources(): Int {
        return R.layout.home_fragment
    }

    override fun initView() {
        mPresenter = open.moive.mvp.presenter.HomePresenter(context!!, this)
        mPresenter?.start()
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = open.moive.adapter.HomeAdatper(context!!, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                var lastPositon = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPositon == mList.size - 1) {
                    if (data != null) {
                        mPresenter?.moreData(data)
                    }

                }
            }
        })

    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            mPresenter?.start()
        }
    }
}