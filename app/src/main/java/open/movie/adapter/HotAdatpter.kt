package open.movie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by lvruheng on 2017/7/6.
 */
class HotAdatpter(fm: FragmentManager, list: ArrayList<Fragment>, titles: MutableList<String>) : FragmentPagerAdapter(fm) {
    var mFm: FragmentManager = fm
    var mList: ArrayList<Fragment> = list
    var mTitles: MutableList<String> = titles
    override fun getItem(position: Int): Fragment {
        return mList[position]

    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

}