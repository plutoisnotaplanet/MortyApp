package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import com.plutoisnotaplanet.mortyapp.application.domain.model.EmailValidationState
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.model.PasswordValidationState
import com.plutoisnotaplanet.mortyapp.application.domain.repository.AuthRepository
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.AuthUseCase
import com.plutoisnotaplanet.mortyapp.application.utils.ValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val validationUtil: ValidationUtil,
    private val authRepository: AuthRepository,
    private val preferences: MortyPreferences
): AuthUseCase {

    override suspend fun resetPassword(email: String): Flow<Response<Boolean>> =
        authRepository.resetPassword(email.trim())

    override suspend fun signIn(email: String, password: String): Flow<Response<Boolean>> =
        authRepository.signIn(email.trim(), password.trim()).map { response ->
            if (response is Response.Success) {
                preferences.apply {
                    this.email = email
                    this.password = password
                    this.isLogged = true
                }
            }
            response
        }

    override suspend fun signUp(email: String, password: String): Flow<Response<Boolean>> =
        authRepository.signUp(email.trim(), password.trim())

    override fun validateLogin(text: String): Flow<InputState> =
        flow {

            val state = validationUtil.validateEmail(text)

            val result = when(state) {
                EmailValidationState.Invalid -> InputState.Error(R.string.tv_email_is_not_valid)
                EmailValidationState.Empty -> InputState.Initialize
                else -> InputState.Valid
            }
            emit(result)
        }

    override fun validatePassword(text: String): Flow<InputState> =
        flow {
            val state = validationUtil.validatePassword(text)

            val result = when(state) {
                PasswordValidationState.Invalid -> InputState.Error(R.string.tv_password_is_invalid)
                PasswordValidationState.Long -> InputState.Error(R.string.tv_password_is_too_long)
                PasswordValidationState.Short -> InputState.Error(R.string.tv_password_is_too_short)
                PasswordValidationState.Empty -> InputState.Initialize
                else -> InputState.Valid
            }
            emit(result)
        }
}