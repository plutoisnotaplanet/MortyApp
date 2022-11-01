package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.AuthUseCase
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : BaseViewModel<WelcomeUiState>() {

    override val _uiState: MutableState<WelcomeUiState> = mutableStateOf(WelcomeUiState.Initialize)

    var loginInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    val loginErrorState by derivedStateOf {
        authUseCase.validateLogin(loginInput)
    }

    val passwordErrorState by derivedStateOf {
        authUseCase.validatePassword(passwordInput)
    }

    fun isApplyEnabled(loginState: InputState, passwordState: InputState): Boolean {
        return if (uiState.value == WelcomeUiState.ForgotPasswordInput) {
            loginState is InputState.Valid
        } else {
            loginState is InputState.Valid && passwordState is InputState.Valid
        }
    }

    fun clearInputs() {
        updateLogin("")
        updatePassword("")
    }

    fun updateLogin(input: String) {
        loginInput = input
    }

    fun updatePassword(input: String) {
        passwordInput = input
    }

    fun onApplyAction() {
        viewModelScope.launchWithCatchOnIo {
            authUseCase.validateLogin(loginInput)
                .combine(authUseCase.validatePassword(passwordInput)) { loginState, passwordState ->
                    isApplyEnabled(loginState, passwordState)
                }.onEach { isValid ->
                    if (isValid) {
                        when (uiState.value) {
                            WelcomeUiState.ForgotPasswordInput -> resetPassword()
                            WelcomeUiState.LoginInputs -> signIn()
                            WelcomeUiState.RegistrationInputs -> signUp()
                            else -> {}
                        }
                    }
                }.collect()
        }
    }

    private suspend fun resetPassword() {
        authUseCase.resetPassword(loginInput).collect { response ->
            when (response) {
                is Response.Error -> showSnack(response.error.message)
                else -> {
                    showSnack(R.string.tv_email_restore_password)
                    clearInputs()
                    updateUiState(WelcomeUiState.LoggedOut)
                }
            }
        }
    }

    private suspend fun signIn() {
        authUseCase.signIn(loginInput, passwordInput).collect { response ->
            when (response) {
                is Response.Error -> showSnack(response.error.message)
                else -> updateUiState(WelcomeUiState.LoggedIn)
            }
        }
    }

    private suspend fun signUp() {
        authUseCase.signUp(loginInput, passwordInput).collect { response ->
            when (response) {
                is Response.Error -> showSnack(response.error.message)
                else -> {
                    showSnack(R.string.tv_success_sign_up)
                    clearInputs()
                    updateUiState(WelcomeUiState.LoggedOut)
                }
            }
        }
    }

}