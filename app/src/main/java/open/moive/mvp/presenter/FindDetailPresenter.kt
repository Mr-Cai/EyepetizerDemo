package open.moive.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.moive.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class FindDetailPresenter(context: Context, view: open.moive.mvp.contract.FindDetailContract.View) : open.moive.mvp.contract.FindDetailContract.Presenter {


    var mContext: Context? = null
    var mView: open.moive.mvp.contract.FindDetailContract.View? = null
    val mModel: open.moive.mvp.model.FindDetailModel by lazy {
        open.moive.mvp.model.FindDetailModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(categoryName: String, strategy: String) {
        val observable: Observable<open.moive.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, categoryName, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.moive.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

    fun requesMoreData(start: Int, categoryName: String, strategy: String) {
        val observable: Observable<open.moive.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadMoreData(mContext!!, start, categoryName, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.moive.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}