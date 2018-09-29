package open.moive.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.moive.mvp.contract.HomeContract
import open.moive.mvp.model.HomeModel
import open.moive.utils.applySchedulers


/**
 * Created by lvruheng on 2017/7/5.
 */
class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {
    var mContext: Context? = null
    var mView: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<open.moive.mvp.model.bean.HomeBean>? = mContext?.let { mModel.loadData(it, true, "0") }
        observable?.applySchedulers()!!.subscribe { homeBean: open.moive.mvp.model.bean.HomeBean ->
            mView?.setData(homeBean)
        }
    }

    fun moreData(data: String?) {
        val observable: Observable<open.moive.mvp.model.bean.HomeBean>? = mContext?.let { mModel.loadData(it, false, data) }
        observable?.applySchedulers()!!.subscribe { homeBean: open.moive.mvp.model.bean.HomeBean ->
            mView?.setData(homeBean)
        }
    }


}




