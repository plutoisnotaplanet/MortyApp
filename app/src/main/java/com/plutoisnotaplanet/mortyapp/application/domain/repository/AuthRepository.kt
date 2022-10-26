package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isLogged: Boolean

    val currentUser: FirebaseUser?

    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>

    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>

    suspend fun resetPassword(email: String): Flow<Response<Boolean>>
}