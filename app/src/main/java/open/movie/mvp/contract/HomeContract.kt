package open.movie.mvp.contract

import open.movie.base.BasePresenter
import open.movie.base.BaseView
import open.movie.mvp.model.bean.HomeBean

interface HomeContract {
    interface View : BaseView<Presenter> {
        fun setData(bean: HomeBean)
    }

    interface Presenter : BasePresenter {
        fun requestData()
    }
}