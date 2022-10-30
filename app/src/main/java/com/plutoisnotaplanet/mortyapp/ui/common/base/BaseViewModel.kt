package com.plutoisnotaplanet.mortyapp.ui.common.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber

abstract class BaseViewModel<T>: ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable.message)
    }

    protected fun CoroutineScope.launchWithCatchOnIo(block: suspend CoroutineScope.() -> Unit) =
        launch(exceptionHandler + Dispatchers.IO) {
            block()
        }

    private val _singleAction: MutableSharedFlow<BaseAction> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val singleAction: SharedFlow<BaseAction> = _singleAction.asSharedFlow()

    protected abstract val _uiState: MutableState<T>
    val uiState: State<T> by lazy { _uiState }

    fun updateUiState(newState: T) {
        _uiState.value = newState
    }

    fun setAction(action: BaseAction) {
        viewModelScope.launch {
            _singleAction.emit(action)
        }
    }

    fun showSnack(message: Int) {
        viewModelScope.launch {
            _singleAction.emit(BaseAction.ShowResourceSnack(message))
        }
    }

    fun showSnack(message: String?) {
        if (message != null) {
            viewModelScope.launch {
                _singleAction.emit(BaseAction.ShowStringSnack(message))
            }
        }
    }



}

