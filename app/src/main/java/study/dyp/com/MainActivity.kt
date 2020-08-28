package study.dyp.com

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.*
import study.dyp.com.common.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    //    private lateinit var tvHello: TextView
    private val job = Job()
    private val scope = CoroutineScope(job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(common_title as Toolbar?)

        val item = arrayOf(
            "跳转到SecondActivity",
            "网络请求测试1",
            "网络请求测试2",
            "网络请求测试3",
            "网络请求测试4",
            "网络请求测试5",
            "水果列表",
            "卡片"
        )

        lv_list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)
        lv_list.setOnItemClickListener { _, _, position, _ ->
            when (item[position]) {
                "跳转到SecondActivity" -> startTargetActivity<SecondActivity>(this@MainActivity)
                "网络请求测试1" -> {
                    sendHttpRequest("https://www.baidu.com", object : LoadListener<String> {
                        override fun onResult(result: QvResult<String>) {
                            toast(
                                this@MainActivity,
                                result.toString() + "_" + result.code.retSuccess()

                            )
                            val ret = 123.retSuccess()
                        }
                    })
                }
                "网络请求测试2" -> {
                    testHttpRequest("https://www.baidu.com", object : SimpleLoadListener {
                        override fun onResult(code: Int) {
                            toast(this@MainActivity, "Code = $code")
                        }
                    })
                }
                "网络请求测试3" -> {
                    sendHttpRequest("https://www.baidu.com") {
                        toast(this@MainActivity, toString())
                    }
                }
                "网络请求测试4" -> {
                    testHttpRequest("https://www.baidu.com") {
                        toast(this@MainActivity, "code = $this , ${retSuccess()}")
                    }
                }
                "网络请求测试5" -> {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Test")
                        .setMessage("123")
                        .setPositiveButton("ok") { dialog, which ->

                        }
                        .setNegativeButton("cancel") { dialog, which ->

                        }
                        .show()

                    scope.launch {
                        try {
                            val ret = testHttpRequest("https://www.baidu.com")
                            toast(this@MainActivity, ret)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                "水果列表" -> {
                    startTargetActivity<FruitActivity>(this)
                }
                "卡片" -> {
                    startTargetActivity<CardActivity>(this)
                }
            }
        }
    }

    fun sendHttpRequest(address: String, loadListener: LoadListener<String>) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                loadListener.onResult(QvResult(-1))
            }

            override fun onResponse(call: Call, response: Response) {
                loadListener.onResult(QvResult(response.code))
            }
        })
    }

    fun sendHttpRequest(address: String, loadListener: QvResult<String>.() -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                loadListener(QvResult(-1))
            }

            override fun onResponse(call: Call, response: Response) {
                loadListener(QvResult(response.code))
            }
        })
    }

    fun testHttpRequest(address: String, loadListener: SimpleLoadListener) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                loadListener.onResult(-1)
            }

            override fun onResponse(call: Call, response: Response) {
                loadListener.onResult(RET_SUCCESS)
            }
        })
    }

    fun testHttpRequest(address: String, loadListener: Int.() -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                loadListener(-1)
            }

            override fun onResponse(call: Call, response: Response) {
                loadListener(RET_SUCCESS)
            }
        })
    }

    suspend fun testHttpRequest(address: String): String {
        return suspendCoroutine {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(address)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    it.resume(response.headers.toString())
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.add ->
//        }
        return true
    }

    private fun initView() {
    }
}
