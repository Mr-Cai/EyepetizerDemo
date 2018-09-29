package open.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_advise.*
import open.movie.R

/**
 * Created by lvruheng on 2017/7/11.
 */
class AdviseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advise)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        settoolbar()
    }

    fun settoolbar() {
        setSupportActionBar(toolbar)
        var bar = supportActionBar
        bar?.title = "意见反馈"
        bar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}