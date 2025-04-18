package com.example.newsapp.core.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.NewsApplication
import com.example.newsapp.common.extension.errorMessage
import com.example.newsapp.common.extension.runOnUi
import com.example.newsapp.common.utils.AppDispatchers
import com.github.ajalt.timberkt.i
import com.github.ajalt.timberkt.w
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent

/**
 * @author Andika Bratadirja
 * @date 17/04/2025
 */
abstract class BaseViewModel : AndroidViewModel(NewsApplication.instance), KoinComponent {

    private val disposables = CompositeDisposable()

    fun launchDisposable(job: () -> Disposable) {
        disposables.add(job())
    }

    fun dispose() {
        disposables.clear()
    }

    internal fun <T> launchOnViewModelScope(block: suspend () -> LiveData<T>): LiveData<T> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emitSource(block())
        }
    }

    fun launchOnUi(
        onRequest: suspend CoroutineScope.() -> Unit,
        onError: (String) -> Unit,
        onFinally: suspend CoroutineScope.() -> Unit = {},
        handleCancellationExceptionManually: Boolean = true
    ): Job {
        return runOnUi {
            tryCatch(onRequest, onError, onFinally, handleCancellationExceptionManually)
        }
    }

    fun launchOnUi(
        dispatcher: AppDispatchers,
        onRequest: suspend CoroutineScope.() -> Unit,
        onError: (String) -> Unit,
        onFinally: suspend CoroutineScope.() -> Unit = {},
        handleCancellationExceptionManually: Boolean = true
    ): Job {
        return runOnUi(dispatcher = dispatcher) {
            tryCatch(onRequest, onError, onFinally, handleCancellationExceptionManually)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: (String) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    val error = e.errorMessage()
                    catchBlock(error)
                } else {
                    w { "Close exception" }
                }
            } finally {
                finallyBlock()
            }
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
        i { "ViewModel Cleared" }
    }
}