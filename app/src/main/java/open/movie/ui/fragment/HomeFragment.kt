package open.movie.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_fragment.*
import open.movie.R
import open.movie.adapter.HomeAdapter
import open.movie.utils.mvp.HomeBean
import open.movie.utils.mvp.HomeBean.IssueListBean.ItemListBean
import open.movie.utils.mvp.HomeContract
import open.movie.utils.mvp.HomePresenter
import java.util.*
import java.util.regex.Pattern

class HomeFragment : BaseFragment(), HomeContract.View {
    lateinit var presenter: HomePresenter
    var itemList = ArrayList<ItemListBean>()
    private lateinit var homeAdapter: HomeAdapter
    lateinit var data: String
    override fun setData(bean: HomeBean) {
        val matcher = Pattern.compile("[^0-9]").matcher(bean.nextPageUrl)
        data = matcher.replaceAll("").subSequence(1, matcher.replaceAll("").length - 1).toString()
        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach { itemList.add(it) }
        homeAdapter.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int = R.layout.home_fragment

    override fun initView() {
        presenter = HomePresenter(context!!, this)
        presenter.start()
        recyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(context!!, itemList)
        recyclerView.adapter = homeAdapter
        refreshLayout.setOnRefreshListener {
            itemList.clear()
            presenter.start()
            refreshLayout.isRefreshing = false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition() //获取显示的选项位置
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == itemList.size - 1) {
                    presenter.moreData(data) //获取更多数据
                }
            }
        })
    }
}