package open.moive.ui.fragment

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.rank_fragment.*
import open.moive.R
import open.moive.adapter.RankAdapter
import open.moive.mvp.contract.HotContract
import open.moive.mvp.model.bean.HotBean
import open.moive.mvp.presenter.HotPresenter

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
        mAdapter = open.moive.adapter.RankAdapter(context!!, mList)
        recyclerView.adapter = mAdapter
        if (arguments != null) {
            mStrategy = arguments!!.getString("strategy")
            mPresenter = open.moive.mvp.presenter.HotPresenter(context!!, this)
            mPresenter.requestData(mStrategy)
        }

    }

    override fun setData(bean: open.moive.mvp.model.bean.HotBean) {
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