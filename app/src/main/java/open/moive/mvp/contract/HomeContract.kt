package open.moive.mvp.contract

/**
 * Created by lvruheng on 2017/7/5.
 */
interface HomeContract{
    interface View : open.moive.base.BaseView<open.moive.mvp.contract.HomeContract.Presenter> {
        fun setData(bean : open.moive.mvp.model.bean.HomeBean)
    }
    interface Presenter : open.moive.base.BasePresenter {
        fun requestData()
    }
}