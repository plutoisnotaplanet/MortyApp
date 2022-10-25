package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import com.plutoisnotaplanet.mortyapp.application.domain.model.error
import com.plutoisnotaplanet.mortyapp.application.domain.model.success
import com.plutoisnotaplanet.mortyapp.application.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {

    override fun resetPassword(email: String): Flow<NetworkResponse<Boolean>> =
        callbackFlow {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { taskResult ->
                    if (taskResult.isSuccessful) {
                        trySend(success(true))
                    }
                }
                .addOnFailureListener { error ->
                    trySend(error(error.message))
                }

            awaitClose {  }
        }

    override fun signIn(email: String, password: String): Flow<NetworkResponse<Boolean>> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    trySend(success(true))
                }
            }
            .addOnFailureListener { error ->
                trySend(error(error.message))
            }

        awaitClose {  }
    }

    override fun signUp(email: String, password: String): Flow<NetworkResponse<Boolean>> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { taskResult ->
                if (taskResult.isSuccessful) {
                    trySend(success(true))
                }
            }
            .addOnFailureListener { error ->
                Timber.e("$error")
                trySend(error(error.message))
            }

        awaitClose {

        }
    }
}