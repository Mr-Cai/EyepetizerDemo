package open.moive.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.moive.mvp.contract.ResultContract
import open.moive.mvp.model.ResultModel
import open.moive.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class ResultPresenter(context: Context, view: ResultContract.View) : ResultContract.Presenter {


    var mContext: Context? = null
    var mView: ResultContract.View? = null
    val mModel: ResultModel by lazy {
        ResultModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(query: String, start: Int) {
        val observable: Observable<open.moive.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, query, start) }
        observable?.applySchedulers()!!.subscribe { bean: open.moive.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}