package open.movie.mvp.model

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.model.bean.HotBean
import open.movie.network.ApiService
import open.movie.network.RetrofitClient

class ResultModel {
    fun loadData(context: Context, query: String, start: Int): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSearchData(10, query, start)
    }
}
