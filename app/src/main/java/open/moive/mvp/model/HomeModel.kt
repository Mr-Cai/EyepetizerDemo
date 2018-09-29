package open.moive.mvp.model

import android.content.Context
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/5.
 */
class HomeModel{
    fun loadData(context: Context,isFirst: Boolean,data: String?): Observable<open.moive.mvp.model.bean.HomeBean>? {
        val retrofitClient = open.moive.network.RetrofitClient.getInstance(context, open.moive.network.ApiService.BASE_URL)
        val apiService  = retrofitClient.create(open.moive.network.ApiService::class.java)
        when(isFirst) {
            true -> return apiService?.getHomeData()

            false -> return apiService?.getHomeMoreData(data.toString(), "2")
        }
    }
}