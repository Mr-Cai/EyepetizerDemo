package open.movie.mvp.model

import android.content.Context
import io.reactivex.Observable
import open.movie.mvp.model.bean.HotBean
import open.movie.network.ApiService
import open.movie.network.RetrofitClient

class HotModel {
    fun loadData(context: Context, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHotData(10, strategy!!, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }
}