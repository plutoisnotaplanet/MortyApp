package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.runtime.Immutable

@Immutable
sealed class WelcomeUiState {
    object Initialize : WelcomeUiState()
    object LoggedIn: WelcomeUiState()
    object LoggedOut: WelcomeUiState()
    object LoginInputs: WelcomeUiState()
    object RegistrationInputs: WelcomeUiState()
    object ForgotPasswordInput: WelcomeUiState()
}