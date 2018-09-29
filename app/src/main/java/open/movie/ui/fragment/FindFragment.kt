package open.movie.ui.fragment

import android.content.Intent
import kotlinx.android.synthetic.main.find_fragment.*
import open.movie.R
import open.movie.ui.FindDetailActivity

/**
 * Created by lvruheng on 2017/7/4.
 */
class FindFragment : BaseFragment(), open.movie.mvp.contract.FindContract.View {
    var mPresenter: open.movie.mvp.presenter.FindPresenter? = null
    var mAdapter: open.movie.adapter.FindAdapter? = null
    var mList: MutableList<open.movie.mvp.model.bean.FindBean>? = null
    override fun setData(beans: MutableList<open.movie.mvp.model.bean.FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        mAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {
        return R.layout.find_fragment
    }

    override fun initView() {
        mPresenter = open.movie.mvp.presenter.FindPresenter(context!!, this)
        mPresenter?.start()
        mAdapter = open.movie.adapter.FindAdapter(context!!, mList)
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