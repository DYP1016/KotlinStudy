package study.dyp.com.common

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

open class BaseApp : Application() {
    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }
}