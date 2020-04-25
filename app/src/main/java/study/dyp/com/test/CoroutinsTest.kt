package study.dyp.com.test

import kotlinx.coroutines.*

fun main() {
    println("test")

    val job = Job()
    val scope = CoroutineScope(job)

//    scope.launch {
//        for (i in 1..10) {
//            println("a = $i")
//            delay(1000)
//        }
//    }
//    scope.launch {
//        for (i in 1..10) {
//            println("b = $i")
//            delay(2000)
//        }
//    }
//    scope.launch {
//        val ret = async {
//            delay(1000)
//            0
//        }
//        println("ret = $ret")
//    }
    val start = System.currentTimeMillis()

    runBlocking {
        val ret = async {
            delay(1000)
            10
        }
        val ret2 = async {
            delay(1000)
            10
        }

        println("ret = ${ret.await()}, 2 = ${ret2.await()}")
    }

    println("end=========> ${System.currentTimeMillis() - start}ms")
    job.cancel()
}