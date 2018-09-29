package open.moive.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.moive.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class HotPresenter(context: Context, view: open.moive.mvp.contract.HotContract.View) : open.moive.mvp.contract.HotContract.Presenter {


    var mContext: Context? = null
    var mView: open.moive.mvp.contract.HotContract.View? = null
    val mModel: open.moive.mvp.model.HotModel by lazy {
        open.moive.mvp.model.HotModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(strategy: String) {
        val observable: Observable<open.moive.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.moive.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}