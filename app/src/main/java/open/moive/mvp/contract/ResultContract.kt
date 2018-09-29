package open.moive.mvp.contract

/**
 * Created by lvruheng on 2017/7/11.
 */
interface ResultContract {
    interface View : open.moive.base.BaseView<open.moive.mvp.contract.ResultContract.Presenter> {
        fun setData(bean: open.moive.mvp.model.bean.HotBean)
    }

    interface Presenter : open.moive.base.BasePresenter {
        fun requestData(query: String, start: Int)
    }
}