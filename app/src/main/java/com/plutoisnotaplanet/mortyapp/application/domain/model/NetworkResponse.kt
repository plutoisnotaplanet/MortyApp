package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.runtime.Composable

sealed class NetworkResponse<out T> {

    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val message: String?) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}

inline fun  <T: Any> runResulting(block: () -> T): NetworkResponse<T> {
    return try {
        NetworkResponse.Success(block())
    } catch (e: Throwable) {
        NetworkResponse.Error(e.message)
    }
}

fun error(message: String?): NetworkResponse.Error = NetworkResponse.Error(message)

fun <T: Any> success(result: T): NetworkResponse<T> = NetworkResponse.Success(result)
