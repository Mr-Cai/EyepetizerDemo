package open.movie.mvp.contract

/**
 * Created by lvruheng on 2017/7/11.
 */
interface ResultContract {
    interface View : open.movie.base.BaseView<open.movie.mvp.contract.ResultContract.Presenter> {
        fun setData(bean: open.movie.mvp.model.bean.HotBean)
    }

    interface Presenter : open.movie.base.BasePresenter {
        fun requestData(query: String, start: Int)
    }
}