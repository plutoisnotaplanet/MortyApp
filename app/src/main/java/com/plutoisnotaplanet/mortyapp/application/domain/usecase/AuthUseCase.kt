package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun resetPassword(email: String): Flow<Response<Boolean>>

    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>

    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>

    fun validateLogin(text: String): Flow<InputState>

    fun validatePassword(text: String): Flow<InputState>
}