package com.plutoisnotaplanet.mortyapp.application.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    var currentStatus: Status

    val hasConnection: Boolean

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}