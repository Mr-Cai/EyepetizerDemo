package open.movie.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_fragment.*
import open.movie.R
import open.movie.adapter.HomeAdapter
import open.movie.utils.mvp.HomeContract
import open.movie.utils.mvp.HomeBean
import open.movie.utils.mvp.HomeBean.IssueListBean.ItemListBean
import open.movie.utils.mvp.HomePresenter
import java.util.*
import java.util.regex.Pattern

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {
    private var mIsRefresh: Boolean = false
    lateinit var mPresenter: HomePresenter
    var mList = ArrayList<ItemListBean>()
    private lateinit var mAdapter: HomeAdapter
    lateinit var data: String
    override fun setData(bean: HomeBean) {
        val m = Pattern.compile("[^0-9]").matcher(bean.nextPageUrl)
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
        mAdapter.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int = R.layout.home_fragment

    override fun initView() {
        mPresenter = HomePresenter(context!!, this)
        mPresenter.start()
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeAdapter(context!!, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition() //获取显示的选项位置
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList.size - 1) {
                    mPresenter.moreData(data) //获取更多数据
                }
            }
        })

    }

    override fun onRefresh() {
        if (!mIsRefresh) {
            mIsRefresh = true
            mPresenter.start()
        }
    }
}