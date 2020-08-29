package study.dyp.com.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
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
        viewModel = ViewModelProviders.of(this, TestViewModelFactory(count))
            .get(TestViewModel::class.java)
        bt_plus.setOnClickListener {
            viewModel.counter++
            refreshCounter()
        }
        bt_clear.setOnClickListener {
            viewModel.counter = 0
            refreshCounter()
        }

        refreshCounter()
    }

    private fun refreshCounter() {
        tv_info.text = viewModel.counter.toString()
    }

    override fun onPause() {
        super.onPause()
        logI("save count: ${viewModel.counter}")
        SpUtil.setCurrentCount(viewModel.counter)
    }
}