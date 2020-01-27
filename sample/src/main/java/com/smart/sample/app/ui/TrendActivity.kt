package com.smart.sample.app.ui

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.compact.app.CompactActivity
import com.compact.app.extensions.R.string.invalid_email
import com.compact.app.extensions.phone
import com.smart.sample.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class TrendActivity : CompactActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var viewModel: TrendViewModel
    private lateinit var adapter: TrendAdapter

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate() {

        viewModel = ViewModelProviders.of(this, factory).get(TrendViewModel::class.java)

        var recyclerView = findViewById<RecyclerView>(R.id.list)
        adapter = TrendAdapter().also {
            recyclerView.adapter = it
        }

        text.phone(invalid_email)
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
                viewModel.loading().subscribe(::println),
                viewModel.trendsResource().subscribe({
                    adapter.swap(it.data())
                }, {
                    Log.d("Resource", "error: $it")
                })
        )
    }
}
