package open.movie.utils.mvp

import android.content.Context
import io.reactivex.Observable
import open.movie.utils.network.ApiService
import open.movie.utils.network.RetrofitClient

class HomeModel {
    fun loadData(context: Context, isFirst: Boolean, data: String): Observable<HomeBean> {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return when (isFirst) {
            true -> apiService!!.getHomeData()
            false -> apiService!!.getHomeMoreData(data, "2")
        }
    }
}

class ResultModel {
    fun loadData(context: Context, query: String, start: Int): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSearchData(10, query, start)
    }
}

class HotModel {
    fun loadData(context: Context, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHotData(10, strategy!!, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }
}

class FindModel {
    fun loadData(context: Context): Observable<MutableList<FindBean>>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindData()
    }
}

class FindDetailModel {
    fun loadData(
            context: Context,
            categoryName: String,
            strategy: String?
    ): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindDetailData(
                categoryName,
                strategy!!,
                "26868b32e808498db32fd51fb422d00175e179df",
                83
        )
    }

    fun loadMoreData(
            context: Context,
            start: Int,
            categoryName: String,
            strategy: String?
    ): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getFindDetailMoreData(start, 10, categoryName, strategy!!)
    }
}