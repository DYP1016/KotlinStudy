package study.dyp.com.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

const val RET_SUCCESS = 0

fun logD(info: String) {
    Log.d(generateTag(getCallerStackTraceElement()), info)
}

fun logI(info: String, tag: String = generateTag(getCallerStackTraceElement())) {
    Log.i(tag, info)
}

fun getCallerStackTraceElement(): StackTraceElement {
    return Thread.currentThread().stackTrace[4]
}

fun toast(context: Activity, info: String) {
    context.runOnUiThread {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show()
    }
}

fun Int.retSuccess(): Boolean {
    return this == RET_SUCCESS
}

inline fun <reified T> startTargetActivity(
    context: Context, block: Intent.() -> Unit = {

    }
) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

data class QvResult<T>(var code: Int, var result: T?) {
    constructor(code: Int) : this(code, null)

    constructor(result: T) : this(RET_SUCCESS, result)

    fun retSuccess() = code == RET_SUCCESS
}

typealias loadListener<T> = QvResult<T>.() -> Unit

typealias simpleLoadListener = Int.() -> Unit

interface LoadListener<T> {
    fun onResult(result: QvResult<T>)
}

interface SimpleLoadListener {
    fun onResult(code: Int)
}

private fun generateTag(caller: StackTraceElement): String {
    var tag = "%s.%s(L:%d)"
    var callerClazzName = caller.className
    callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
    tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber)
    return tag
}