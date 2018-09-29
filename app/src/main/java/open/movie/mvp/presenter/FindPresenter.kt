package open.movie.mvp.presenter

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.contract.FindContract
import open.movie.mvp.model.FindModel
import open.movie.utils.applySchedulers

/**
 * Created by lvruheng on 2017/7/6.
 */
class FindPresenter(context: Context, view: FindContract.View) : FindContract.Presenter {
    var mContext: Context? = null
    var mView: FindContract.View? = null
    val mModel: FindModel by lazy {
        FindModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<MutableList<open.movie.mvp.model.bean.FindBean>>? = mContext?.let { mModel.loadData(mContext!!) }
        observable?.applySchedulers()!!.subscribe { beans: MutableList<open.movie.mvp.model.bean.FindBean> ->
            mView?.setData(beans)
        }
    }


}