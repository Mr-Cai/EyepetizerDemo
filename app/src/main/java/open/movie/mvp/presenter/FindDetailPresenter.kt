package open.movie.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.movie.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/7.
 */
class FindDetailPresenter(context: Context, view: open.movie.mvp.contract.FindDetailContract.View) : open.movie.mvp.contract.FindDetailContract.Presenter {


    var mContext: Context? = null
    var mView: open.movie.mvp.contract.FindDetailContract.View? = null
    val mModel: open.movie.mvp.model.FindDetailModel by lazy {
        open.movie.mvp.model.FindDetailModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {

    }

    override fun requestData(categoryName: String, strategy: String) {
        val observable: Observable<open.movie.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadData(mContext!!, categoryName, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.movie.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

    fun requesMoreData(start: Int, categoryName: String, strategy: String) {
        val observable: Observable<open.movie.mvp.model.bean.HotBean>? = mContext?.let { mModel.loadMoreData(mContext!!, start, categoryName, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: open.movie.mvp.model.bean.HotBean ->
            mView?.setData(bean)
        }
    }

}