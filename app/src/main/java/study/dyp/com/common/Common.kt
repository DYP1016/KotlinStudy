package study.dyp.com.common

import android.util.Log

fun logD(info: String) {
    Log.d(generateTag(getCallerStackTraceElement()), info)
}

fun logI(info: String, tag: String = generateTag(getCallerStackTraceElement())) {
    Log.i(tag, info)
}

fun getCallerStackTraceElement(): StackTraceElement {
    return Thread.currentThread().stackTrace[4]
}

private fun generateTag(caller: StackTraceElement): String {
    var tag = "%s.%s(L:%d)"
    var callerClazzName = caller.className
    callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
    tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber)
    return tag
}