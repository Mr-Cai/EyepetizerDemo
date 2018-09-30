package open.movie.utils.mvp

import android.annotation.SuppressLint
import android.content.Context
import io.reactivex.Observable
import open.movie.utils.applySchedulers

@SuppressLint("CheckResult")
class FindDetailPresenter(var context: Context, var view: FindDetailContract.View) :
        FindDetailContract.Presenter {
    private val findDetailModel: FindDetailModel by lazy {
        FindDetailModel()
    }

    override fun start() {

    }

    override fun requestData(categoryName: String, strategy: String) {
        val observable: Observable<HotBean>? = context.let {
            findDetailModel.loadData(context, categoryName, strategy)
        }
        observable?.applySchedulers()!!.subscribe { bean: HotBean ->
            view.setData(bean)
        }
    }

    fun requestMoreData(start: Int, categoryName: String, strategy: String) {
        val observable: Observable<HotBean>? = context.let {
            findDetailModel.loadMoreData(context, start, categoryName, strategy)
        }
        observable?.applySchedulers()!!.subscribe { bean: HotBean ->
            view.setData(bean)
        }
    }
}

@SuppressLint("CheckResult")
class FindPresenter(var context: Context, var view: FindContract.View) : FindContract.Presenter {
    private val findModel: FindModel by lazy {
        FindModel()
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<MutableList<FindBean>>? = context.let { findModel.loadData(context) }
        observable?.applySchedulers()!!.subscribe { beans: MutableList<FindBean> ->
            view.setData(beans)
        }
    }
}

@SuppressLint("CheckResult")
class HomePresenter(
        private var context: Context,
        private var view: HomeContract.View
) : HomeContract.Presenter {
    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    override fun start() {
        requestData()
    }

    override fun requestData() {
        val observable: Observable<HomeBean> = context.let { mModel.loadData(it, true, "0") }
        observable.applySchedulers().subscribe { homeBean: HomeBean ->
            view.setData(homeBean)
        }
    }

    fun moreData(data: String) {
        val observable: Observable<HomeBean> = context.let { mModel.loadData(it, false, data) }
        observable.applySchedulers().subscribe { homeBean: HomeBean ->
            view.setData(homeBean)
        }
    }
}

@SuppressLint("CheckResult")
class HotPresenter(var context: Context, var view: HotContract.View) : HotContract.Presenter {
    private val hotModel: HotModel by lazy {
        HotModel()
    }

    override fun start() {

    }

    override fun requestData(strategy: String) {
        val observable: Observable<HotBean>? = context.let { hotModel.loadData(context, strategy) }
        observable?.applySchedulers()!!.subscribe { bean: HotBean ->
            view.setData(bean)
        }
    }

}

@SuppressLint("CheckResult")
class ResultPresenter(var context: Context, var view: ResultContract.View) : ResultContract.Presenter {
    private val mModel: ResultModel by lazy {
        ResultModel()
    }

    override fun start() {

    }

    override fun requestData(query: String, start: Int) {
        val observable: Observable<HotBean>? = context.let { mModel.loadData(context, query, start) }
        observable?.applySchedulers()!!.subscribe { bean: HotBean ->
            view.setData(bean)
        }
    }

}