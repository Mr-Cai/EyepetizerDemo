package open.movie.network

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by lvruheng on 2017/7/4.
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {
    private var httpCacheDirectory: File? = null
    val mContext: Context = context
    var cache: Cache? = null
    var okHttpClient: OkHttpClient? = null
    var retrofit: Retrofit? = null
    val DEFAULT_TIMEOUT: Long = 20
    val url = baseUrl

    init {
        //缓存地址
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.cacheDir, "app_cache")
        }
        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }
        //okhttp创建了
        okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)
                .addInterceptor(open.movie.network.CacheInterceptor(context))
                .addNetworkInterceptor(open.movie.network.CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
        //retrofit创建了
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()

    }

    companion object {
        @Volatile
        var instance: open.movie.network.RetrofitClient? = null

        fun getInstance(context: Context, baseUrl: String): open.movie.network.RetrofitClient {
            if (open.movie.network.RetrofitClient.Companion.instance == null) {
                synchronized(open.movie.network.RetrofitClient::class) {
                    if (open.movie.network.RetrofitClient.Companion.instance == null) {
                        open.movie.network.RetrofitClient.Companion.instance = open.movie.network.RetrofitClient(context, baseUrl)
                    }
                }
            }
            return open.movie.network.RetrofitClient.Companion.instance!!
        }


    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }


}