package open.movie.mvp.model

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.model.bean.FindBean
import open.movie.network.ApiService
import open.movie.network.RetrofitClient

class FindModel {
    fun loadData(context: Context): Observable<MutableList<FindBean>>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindData()
    }
}