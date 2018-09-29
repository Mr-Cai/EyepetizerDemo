package open.movie.ui.fragment

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.rank_fragment.*
import open.movie.R
import open.movie.adapter.RankAdapter
import open.movie.mvp.contract.HotContract
import open.movie.mvp.model.bean.HotBean
import open.movie.mvp.presenter.HotPresenter

/**
 * Created by lvruheng on 2017/7/6.
 */
class RankFragment : BaseFragment(), HotContract.View {
    lateinit var mPresenter: HotPresenter
    lateinit var mStrategy: String
    lateinit var mAdapter: RankAdapter
    var mList: ArrayList<HotBean.ItemListBean.DataBean> = ArrayList()
    override fun getLayoutResources(): Int {
        return R.layout.rank_fragment
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = open.movie.adapter.RankAdapter(context!!, mList)
        recyclerView.adapter = mAdapter
        if (arguments != null) {
            mStrategy = arguments!!.getString("strategy")
            mPresenter = open.movie.mvp.presenter.HotPresenter(context!!, this)
            mPresenter.requestData(mStrategy)
        }

    }

    override fun setData(bean: open.movie.mvp.model.bean.HotBean) {
        Log.e("rank", bean.toString())
        if (mList.size > 0) {
            mList.clear()
        }
        bean.itemList?.forEach {
            it.data?.let { it1 -> mList.add(it1) }
        }
        mAdapter.notifyDataSetChanged()
    }

}