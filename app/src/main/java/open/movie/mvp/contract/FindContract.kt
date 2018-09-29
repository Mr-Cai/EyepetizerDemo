package open.movie.mvp.contract

/**
 * Created by lvruheng on 2017/7/6.
 */
interface FindContract{
    interface View : open.movie.base.BaseView<open.movie.mvp.contract.FindContract.Presenter> {
        fun setData(beans : MutableList<open.movie.mvp.model.bean.FindBean>)
    }
    interface Presenter : open.movie.base.BasePresenter {
        fun requestData()
    }
}