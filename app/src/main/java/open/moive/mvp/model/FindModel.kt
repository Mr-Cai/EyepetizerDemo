package open.moive.mvp.model

import android.content.Context
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/6.
 */
class FindModel {
    fun loadData(context: Context): Observable<MutableList<open.moive.mvp.model.bean.FindBean>>? {
        val retrofitClient = open.moive.network.RetrofitClient.getInstance(context, open.moive.network.ApiService.BASE_URL)
        val apiService = retrofitClient.create(open.moive.network.ApiService::class.java)
        return apiService?.getFindData()
    }
}