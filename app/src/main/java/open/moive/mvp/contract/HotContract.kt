package open.moive.mvp.contract

/**
 * Created by lvruheng on 2017/7/5.
 */
interface HotContract{
    interface View : open.moive.base.BaseView<open.moive.mvp.contract.HotContract.Presenter> {
        fun setData(bean : open.moive.mvp.model.bean.HotBean)
    }
    interface Presenter : open.moive.base.BasePresenter {
        fun requestData(strategy: String)
    }
}