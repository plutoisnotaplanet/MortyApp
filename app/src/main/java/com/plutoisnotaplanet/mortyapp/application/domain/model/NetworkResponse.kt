package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.runtime.Composable

sealed class NetworkResponse<out T> {
    object Loading : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String?) : NetworkResponse<Nothing>()

    @Composable
    fun OnLoading(block: @Composable () -> Unit) {
        if (this is Loading) {
            block()
        }
    }

    @Composable
    fun OnSuccess(block: @Composable (T) -> Unit) {
        if (this is Success) {
            block(data)
        }
    }

    @Composable
    fun OnError(block: @Composable (String?) -> Unit) {
        if (this is Error) {
            block(message)
        }
    }
}
