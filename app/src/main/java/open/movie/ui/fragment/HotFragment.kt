package open.movie.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.hot_fragment.*
import open.movie.R
import open.movie.adapter.HotAdatpter

/**
 * Created by lvruheng on 2017/7/4.
 */
class HotFragment : BaseFragment() {
    private var mTabs = listOf("周排行", "月排行", "总排行").toMutableList()
    private lateinit var mFragments: ArrayList<Fragment>
    private val STRATEGY = arrayOf("weekly", "monthly", "historical")
    override fun getLayoutResources(): Int {
        return R.layout.hot_fragment
    }

    override fun initView() {
        val weekFragment = RankFragment()
        val weekBundle = Bundle()
        weekBundle.putString("strategy", STRATEGY[0])
        weekFragment.arguments = weekBundle
        val monthFragment = RankFragment()
        val monthBundle = Bundle()
        monthBundle.putString("strategy", STRATEGY[1])
        monthFragment.arguments = monthBundle
        val allFragment = RankFragment()
        val allBundle = Bundle()
        allBundle.putString("strategy", STRATEGY[2])
        allFragment.arguments = allBundle
        mFragments = ArrayList()
        mFragments.add(weekFragment as Fragment)
        mFragments.add(monthFragment as Fragment)
        mFragments.add(allFragment as Fragment)
        vp_content.adapter = HotAdatpter(fragmentManager!!, mFragments, mTabs)
        tabs.setupWithViewPager(vp_content)
    }

}