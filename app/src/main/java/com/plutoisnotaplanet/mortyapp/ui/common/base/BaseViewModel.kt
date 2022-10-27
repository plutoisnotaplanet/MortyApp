package com.plutoisnotaplanet.mortyapp.ui.common.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseViewModel : ViewModel() {

    private val mutex = Mutex()

    protected val _uiState: MutableState<BaseUiViewState> =
        mutableStateOf(BaseUiViewState.Initialize)

    val uiState: State<BaseUiViewState> by lazy {
        _uiState
    }

    fun showSnack(message: Int) {
        viewModelScope.launch {
            _uiState.value = BaseUiViewState.ShowResourceSnack(message)
            delay(100)
            _uiState.value = BaseUiViewState.Initialize
        }
    }

    fun showSnack(message: String?) {
        if (message != null) {
            viewModelScope.launch {
                _uiState.value = BaseUiViewState.ShowStringSnack(message)
                delay(100)
                _uiState.value = BaseUiViewState.Initialize
            }
        }
    }

    suspend fun showSnackSuspend(message: String?) {
        if (message != null) {
            mutex.withLock {
                _uiState.value = BaseUiViewState.ShowStringSnack(message)
                delay(100)
                _uiState.value = BaseUiViewState.Initialize
            }
        }
    }

    suspend fun showSnackSuspend(message: Int) {
        mutex.withLock {
            _uiState.value = BaseUiViewState.ShowResourceSnack(message)
            delay(100)
            _uiState.value = BaseUiViewState.Initialize
        }
    }

    suspend fun CoroutineScope.setStateSuspend(state: BaseUiViewState) {
        mutex.withLock { _uiState.value = state }
    }

    fun setState(state: BaseUiViewState) {
        _uiState.value = state
    }

}

