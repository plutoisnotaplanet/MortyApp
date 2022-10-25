package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun resetPassword(email: String): Flow<NetworkResponse<Boolean>>

    fun signIn(email: String, password: String): Flow<NetworkResponse<Boolean>>

    fun signUp(email: String, password: String): Flow<NetworkResponse<Boolean>>

    fun validateLogin(text: String): Flow<InputState>

    fun validatePassword(text: String): Flow<InputState>
}