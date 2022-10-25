package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signIn(email: String, password: String): Flow<NetworkResponse<Boolean>>

    fun signUp(email: String, password: String): Flow<NetworkResponse<Boolean>>

    fun resetPassword(email: String): Flow<NetworkResponse<Boolean>>
}