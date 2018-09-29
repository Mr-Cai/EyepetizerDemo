package open.movie.mvp.contract

/**
 * Created by lvruheng on 2017/7/5.
 */
interface HotContract{
    interface View : open.movie.base.BaseView<open.movie.mvp.contract.HotContract.Presenter> {
        fun setData(bean : open.movie.mvp.model.bean.HotBean)
    }
    interface Presenter : open.movie.base.BasePresenter {
        fun requestData(strategy: String)
    }
}