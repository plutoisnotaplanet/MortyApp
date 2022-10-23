package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.runtime.Composable

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String?) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}
