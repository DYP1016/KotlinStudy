package study.dyp.com.common

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

abstract class BaseSpUtil {
    private val sharedPreferences: SharedPreferences by lazy {
        logI("init sp")
        val spName = getSharePreferencesName()
        val sp = BaseApp.context.getSharedPreferences(spName, Context.MODE_PRIVATE)
        sp
    }
    private val editor: Editor by lazy {
        sharedPreferences.edit()
    }

    protected abstract fun getSharePreferencesName(): String

    fun getSP(): SharedPreferences {
        return sharedPreferences
    }

    fun setValue(key: String, value: String?) {
        edit { putString(key, value) }
    }

    fun clear() {
        edit { clear() }
    }

    fun remove(key: String) {
        edit { remove(key) }
    }

    fun setBooleanValue(key: String, value: Boolean?) {
        edit { putBoolean(key, value ?: false) }
    }

    fun getBooleanValue(key: String, defaultValue: Boolean? = null): Boolean {
        return getSP().getBoolean(key, defaultValue ?: false)
    }

    fun setIntValue(key: String, value: Int?) {
        edit { putInt(key, value ?: -1) }
    }

    fun getIntValue(key: String, defaultValue: Int? = null): Int {
        return getSP().getInt(key, defaultValue ?: -1)
    }

    fun getValue(key: String): String {
        return getSP().getString(key, "") ?: ""
    }

    fun getLongValue(key: String, defaultValue: Long? = null): Long {
        return getSP().getLong(key, defaultValue ?: -1)
    }

    fun setLongValue(key: String, value: Long?) {
        edit { putLong(key, value ?: -1) }
    }

    fun edit(block: Editor.() -> Unit) {
        editor.block()
        editor.commit()
    }
}