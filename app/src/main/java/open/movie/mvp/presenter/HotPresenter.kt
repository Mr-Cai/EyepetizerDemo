package open.movie.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.movie.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class HotPresenter(context: Context, view: open.movie.mvp.contract.HotContract.View) : open.movie.mvp.contract.HotContract.Presenter {


    var mContext: Context? = null
    var mView: open.movie.mvp.contract.HotContract.View? = null
    val mModel: open.movie.mvp.model.HotModel by lazy {
        open.movie.mvp.model.HotModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(strategy: String) {
        val observable: Observable<open.movie.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.movie.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}