package open.moive.mvp.model

import android.content.Context
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/11.
 */
class ResultModel {
    fun loadData(context: Context, query: String, start: Int): Observable<open.moive.mvp.model.bean.HotBean>? {
        val retrofitClient = open.moive.network.RetrofitClient.getInstance(context, open.moive.network.ApiService.BASE_URL)
        val apiService = retrofitClient.create(open.moive.network.ApiService::class.java)
        return apiService?.getSearchData(10, query, start)

    }
}
