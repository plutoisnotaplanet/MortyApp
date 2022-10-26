package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.plutoisnotaplanet.mortyapp.application.data.database.DbFindHelper
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.UserProfileEntity
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.model.error
import com.plutoisnotaplanet.mortyapp.application.domain.model.success
import com.plutoisnotaplanet.mortyapp.application.domain.repository.AuthRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dbFindHelper: DbFindHelper
) : AuthRepository {


    override val currentUser: FirebaseUser? = firebaseAuth.currentUser

    private val db: MortyDataBase = dbFindHelper.getDatabase(currentUser?.uid)

    override val isLogged: Boolean = currentUser != null

    override suspend fun resetPassword(email: String): Flow<Response<Boolean>> =
        callbackFlow {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { taskResult ->
                    if (taskResult.isSuccessful) {
                        trySend(success(true))
                    }
                }
                .addOnFailureListener { error ->
                    trySend(error(error))
                }

            awaitClose {
                close()
            }
        }

    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> =
        callbackFlow {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { taskResult ->
                    if (taskResult.isSuccessful) {
                        trySend(success(true))
                    }
                }
                .addOnFailureListener { error ->
                    trySend(error(error))
                }

            awaitClose {
                close()
            }
        }

    override suspend fun signUp(email: String, password: String): Flow<Response<Boolean>> =
        callbackFlow {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { taskResult ->
                    if (taskResult.isSuccessful) {
                        trySend(success(true))
                    }
                }
                .addOnFailureListener { error ->
                    trySend(error(error))
                }

            awaitClose {
                close()
            }
        }
}