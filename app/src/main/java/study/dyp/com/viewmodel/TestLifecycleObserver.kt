package study.dyp.com.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import study.dyp.com.common.logI

class TestLifecycleObserver(private val lifecycle: Lifecycle) : LifecycleObserver {
    private var isRunning = false
    private var current: Lifecycle.State? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityStart() {
        logI("start")
        isRunning = true

        Thread {
            while (isRunning) {
                if (lifecycle.currentState == current) {
                    return@Thread
                }
                current = lifecycle.currentState

                logI("current state: $current")
                Thread.sleep(100)
            }
        }.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityEnd() {
        logI("end")
        isRunning = false
    }
}