package open.movie.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.contract.HomeContract
import open.movie.mvp.model.HomeModel
import open.movie.mvp.model.bean.HomeBean
import open.movie.utils.applySchedulers

class HomePresenter(
        private var mContext: Context,
        private var mView: HomeContract.View
) : HomeContract.Presenter {
    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    override fun start() {
        requestData()
    }

    @SuppressLint("CheckResult")
    override fun requestData() {
        val observable: Observable<HomeBean> = mContext.let { mModel.loadData(it, true, "0") }!!
        observable.applySchedulers().subscribe { homeBean: HomeBean ->
            mView.setData(homeBean)
        }
    }

    @SuppressLint("CheckResult")
    fun moreData(data: String?) {
        val observable: Observable<HomeBean> = mContext.let { mModel.loadData(it, false, data) }!!
        observable.applySchedulers().subscribe { homeBean: HomeBean ->
            mView.setData(homeBean)
        }
    }
}




