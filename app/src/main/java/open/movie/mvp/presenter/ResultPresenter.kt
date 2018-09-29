package open.movie.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.contract.ResultContract
import open.movie.mvp.model.ResultModel
import open.movie.utils.applySchedulers

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
        val observable: Observable<open.movie.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, query, start) }
        observable?.applySchedulers()!!.subscribe { bean: open.movie.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}