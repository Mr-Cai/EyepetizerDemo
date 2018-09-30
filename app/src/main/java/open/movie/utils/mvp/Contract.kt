package open.movie.utils.mvp

interface FindDetailContract {
    interface View {
        fun setData(bean: HotBean)
    }

    interface Presenter {
        fun start()

        fun requestData(categoryName: String, strategy: String)
    }
}

interface FindContract {
    interface View {
        fun setData(beans: MutableList<FindBean>)
    }

    interface Presenter {
        fun start()
        fun requestData()
    }
}

interface HomeContract {
    interface View {
        fun setData(bean: HomeBean)
    }

    interface Presenter {
        fun start()
        fun requestData()
    }
}

interface ResultContract {
    interface View {
        fun setData(bean: HotBean)
    }

    interface Presenter {
        fun start()
        fun requestData(query: String, start: Int)
    }
}

interface HotContract {
    interface View {
        fun setData(bean: HotBean)
    }

    interface Presenter {
        fun start()
        fun requestData(strategy: String)
    }
}