package open.moive.ui.fragment

import android.content.Intent
import kotlinx.android.synthetic.main.find_fragment.*
import open.moive.R
import open.moive.ui.FindDetailActivity

/**
 * Created by lvruheng on 2017/7/4.
 */
class FindFragment : BaseFragment(), open.moive.mvp.contract.FindContract.View {
    var mPresenter: open.moive.mvp.presenter.FindPresenter? = null
    var mAdapter: open.moive.adapter.FindAdapter? = null
    var mList: MutableList<open.moive.mvp.model.bean.FindBean>? = null
    override fun setData(beans: MutableList<open.moive.mvp.model.bean.FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        mAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {
        return R.layout.find_fragment
    }

    override fun initView() {
        mPresenter = open.moive.mvp.presenter.FindPresenter(context!!, this)
        mPresenter?.start()
        mAdapter = open.moive.adapter.FindAdapter(context!!, mList)
        gv_find.adapter = mAdapter
        gv_find.setOnItemClickListener { parent, view, position, id ->
            var bean = mList?.get(position)
            var name = bean?.name
            var intent: Intent = Intent(context, FindDetailActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)

        }
    }

}