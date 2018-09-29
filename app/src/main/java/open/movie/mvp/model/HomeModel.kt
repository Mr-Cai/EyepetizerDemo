package open.movie.mvp.model

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.model.bean.HomeBean
import open.movie.network.ApiService
import open.movie.network.RetrofitClient

class HomeModel {
    fun loadData(context: Context, isFirst: Boolean, data: String?): Observable<HomeBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return when (isFirst) {
            true -> apiService?.getHomeData()
            false -> apiService?.getHomeMoreData(data.toString(), "2")
        }
    }
}