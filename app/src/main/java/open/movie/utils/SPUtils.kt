package open.movie.utils

import android.content.Context
import java.util.*

class SPUtils private constructor(context: Context, spName: String) {
    private val preferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    fun put(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue)!!
    }

    fun put(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun put(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun put(key: String, value: Float) {
        preferences.edit().putFloat(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun put(key: String, values: Set<String>) {
        preferences.edit().putStringSet(key, values).apply()
    }

    val all: Map<String, *> get() = preferences.all
    operator fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    companion object {

        private val sSPMap = HashMap<String, SPUtils>()

        fun getInstance(context: Context, spName: String): SPUtils {
            var name = spName
            if (isSpace(name)) name = "spUtils"
            var sp: SPUtils? = sSPMap[name]
            if (sp == null) {
                sp = SPUtils(context, name)
                sSPMap[name] = sp
            }
            return sp
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
