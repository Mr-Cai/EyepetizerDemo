package open.movie.ui.fragment

import android.content.Intent
import kotlinx.android.synthetic.main.find_fragment.*
import open.movie.R
import open.movie.utils.mvp.FindBean
import open.movie.utils.mvp.FindContract
import open.movie.utils.mvp.FindPresenter
import open.movie.ui.activity.FindDetailActivity

/**
 * Created by lvruheng on 2017/7/4.
 */
class FindFragment : BaseFragment(), FindContract.View {
    private var mPresenter: FindPresenter? = null
    private var mAdapter: open.movie.adapter.FindAdapter? = null
    private var mList: MutableList<FindBean>? = null
    override fun setData(beans: MutableList<FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        mAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {
        return R.layout.find_fragment
    }

    override fun initView() {
        mPresenter = FindPresenter(context!!, this)
        mPresenter?.start()
        mAdapter = open.movie.adapter.FindAdapter(context!!, mList)
        gv_find.adapter = mAdapter
        gv_find.setOnItemClickListener { parent, view, position, id ->
            val bean = mList?.get(position)
            val name = bean?.name
            val intent = Intent(context, FindDetailActivity::class.java)
            intent.putExtra("name", name)
            startActivity(intent)

        }
    }

}