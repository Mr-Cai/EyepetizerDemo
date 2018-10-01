package open.movie.utils.network

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitClient(var context: Context, baseUrl: String) {
    private var httpCacheDirectory = File(context.cacheDir, "app_cache")
    private var cache: Cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
    private var retrofit: Retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    private val timeOut: Long = 20

    //缓存地址
    init {
        OkHttpClient.Builder()
                .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .build()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: RetrofitClient

        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            synchronized(RetrofitClient::class) {
                RetrofitClient.instance = RetrofitClient(context, baseUrl)
            }
            return RetrofitClient.instance
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) throw RuntimeException("开放接口不存在!")
        return retrofit.create(service)
    }
}