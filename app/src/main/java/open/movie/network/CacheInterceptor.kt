package open.movie.network

import android.content.Context
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import open.movie.utils.NetworkUtils

class CacheInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain!!.request()
        return if (NetworkUtils.isNetConneted(context)) {
            val response = chain.proceed(request)
            val maxAge = 60 //预读缓存最多60秒
            val cacheControl = request?.cacheControl().toString()
            Log.e("CacheInterceptor", "6s load cahe$cacheControl")
            response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
            val response = chain.proceed(request)
            val maxStale = 60 * 60 * 24 * 3 //设置缓存记录最多三天
            response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
        }
    }

}