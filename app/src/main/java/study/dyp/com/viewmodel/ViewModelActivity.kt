package study.dyp.com.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_view_model.*
import study.dyp.com.R
import study.dyp.com.common.SpUtil
import study.dyp.com.common.logI

class ViewModelActivity : AppCompatActivity() {
    private lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model)

        lifecycle.addObserver(TestLifecycleObserver(lifecycle))

        val count = SpUtil.getCurrentCount();
        logI("count: $count")
        viewModel =
            ViewModelProvider(this, TestViewModelFactory(count)).get(TestViewModel::class.java)

        bt_plus.setOnClickListener {
            val current = viewModel.counter.value ?: 0
            viewModel.counter.value = current + 1
        }
        bt_clear.setOnClickListener {
            viewModel.counter.value = 0
        }
        bt_get_user.setOnClickListener {
            viewModel.getUser((0..10000).random().toString())
        }

        viewModel.counter.observe(this) {
            tv_info.text = viewModel.counter.value.toString()
        }
        viewModel.user.observe(this) {
            tv_info.text = "Id: ${it.userId}"
        }
    }

    override fun onPause() {
        super.onPause()
        logI("save count: ${viewModel.counter}")
        SpUtil.setCurrentCount(viewModel.counter.value)
    }
}