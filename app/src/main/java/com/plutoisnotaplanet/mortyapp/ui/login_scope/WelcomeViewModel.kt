package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.AuthUseCase
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.launchOnIo
import com.plutoisnotaplanet.mortyapp.ui.common.delegate.MortyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : MortyViewModel<LaunchState>() {

    override var _uiState: MutableState<LaunchState> = mutableStateOf(LaunchState.Initialize)

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

    val isApplyButtonEnabled: (InputState, InputState) -> Boolean
        get() = { loginState, passwordState ->
            loginState is InputState.Valid && passwordState is InputState.Valid
        }

    fun updateLogin(input: String) {
        loginInput = input
    }

    fun updatePassword(input: String) {
        passwordInput = input
    }

    fun updateUiState(newState: LaunchState) {
        _uiState.value = newState
    }

    fun resetPassword() {
        viewModelScope.launchOnIo {
            authUseCase.resetPassword(loginInput).collect { response ->
                when (response) {
                    is Response.Error -> showSnack(response.error.message)
                    else -> {
                        showSnack(R.string.tv_email_restore_password)
                        loginInput = ""
                        passwordInput = ""
                        updateUiState(LaunchState.LoggedOut)
                    }
                }
            }
        }
    }

    fun signIn() {
        viewModelScope.launchOnIo {
            authUseCase.signIn(loginInput, passwordInput).collect { response ->
                when (response) {
                    is Response.Error -> showSnack(response.error.message)
                    else -> updateUiState(LaunchState.LoggedIn)
                }
            }
        }
    }

    fun signUp() {
        viewModelScope.launchOnIo {
            authUseCase.signUp(loginInput, passwordInput).collect { response ->
                when (response) {
                    is Response.Error -> showSnack(response.error.message)
                    else -> {
                        showSnack(R.string.tv_success_sign_up)
                        loginInput = ""
                        passwordInput = ""
                        updateUiState(LaunchState.LoggedOut)
                    }
                }
            }
        }
    }
}