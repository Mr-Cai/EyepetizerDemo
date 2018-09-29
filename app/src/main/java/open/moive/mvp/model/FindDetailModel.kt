package open.moive.mvp.model

import android.content.Context
import io.reactivex.Observable

/**
 * Created by lvruheng on 2017/7/7.
 */
class FindDetailModel {
    fun loadData(context: Context, categoryName: String, strategy: String?): Observable<open.moive.mvp.model.bean.HotBean>? {
        val retrofitClient = open.moive.network.RetrofitClient.getInstance(context, open.moive.network.ApiService.BASE_URL)
        val apiService = retrofitClient.create(open.moive.network.ApiService::class.java)
        return apiService?.getFindDetailData(categoryName, strategy!!, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }
    fun loadMoreData(context: Context,start : Int, categoryName: String, strategy: String?): Observable<open.moive.mvp.model.bean.HotBean>? {
        val retrofitClient = open.moive.network.RetrofitClient.getInstance(context, open.moive.network.ApiService.BASE_URL)
        val apiService = retrofitClient.create(open.moive.network.ApiService::class.java)
        return apiService?.getFindDetailMoreData(start,10,categoryName, strategy!!)
    }
}