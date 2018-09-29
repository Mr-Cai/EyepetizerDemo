package open.moive.mvp.contract

/**
 * Created by lvruheng on 2017/7/6.
 */
interface FindContract{
    interface View : open.moive.base.BaseView<open.moive.mvp.contract.FindContract.Presenter> {
        fun setData(beans : MutableList<open.moive.mvp.model.bean.FindBean>)
    }
    interface Presenter : open.moive.base.BasePresenter {
        fun requestData()
    }
}