package open.movie.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import kotlinx.android.synthetic.main.mine_fragment.*
import open.movie.R
import open.movie.ui.activity.AdviseActivity
import open.movie.ui.activity.CacheActivity
import open.movie.ui.activity.WatchActivity

/**
 * Created by lvruheng on 2017/7/4.
 */
class MineFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_watch -> {
                var intent = Intent(activity, WatchActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_advise -> {
                var intent = Intent(activity, AdviseActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_save -> {
                var intent = Intent(activity, CacheActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getLayoutResources(): Int {
        return R.layout.mine_fragment
    }

    override fun initView() {
        tv_advise.setOnClickListener(this)
        tv_watch.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        tv_advise.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_watch.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_save.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

}