package com.example.newsapp.core.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    lateinit var binding: B

    abstract fun getViewBinding(): B

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        onViewCreated(savedInstanceState)
    }

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseActivity, Observer { data -> data?.let(action) })
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected fun initToolbar(
        toolbar: Toolbar,
        paramTitle: String = "",
        paramSubTitle: String = "",
        paramBooleanBack: Boolean = true
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = paramTitle
        if (paramSubTitle.isNotEmpty())
            supportActionBar?.subtitle = paramSubTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(paramBooleanBack)
    }
}