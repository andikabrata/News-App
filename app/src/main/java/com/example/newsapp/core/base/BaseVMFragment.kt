package com.example.newsapp.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.compat.ViewModelCompat
import kotlin.coroutines.CoroutineContext

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseVMFragment<VM : BaseViewModel, B : ViewBinding> : Fragment(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    protected val viewModel: VM by ViewModelCompat.viewModel(this, getViewModel())

    lateinit var binding: B

    private lateinit var rootView: View

    abstract val layoutResourceId: Int

    abstract fun getViewModel(): Class<VM>

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun onViewCreated(savedInstanceState: Bundle?)

    abstract fun observerViewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container)
        return getLayoutIfDefined()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(savedInstanceState)
        observerViewModel()
    }

    private fun getLayoutIfDefined(): View {
        return  binding.root
    }

    protected fun <T> LiveData<T>.onResult(action: (T) -> Unit) {
        observe(this@BaseVMFragment, Observer { data -> data?.let(action) })
    }
}