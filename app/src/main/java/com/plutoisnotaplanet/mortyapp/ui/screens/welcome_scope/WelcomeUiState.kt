package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.runtime.Immutable
import com.plutoisnotaplanet.mortyapp.ui.common.base.UiState

@Immutable
sealed class WelcomeUiState: UiState() {
    object Initialize : WelcomeUiState()
    object LoggedIn: WelcomeUiState()
    object LoggedOut: WelcomeUiState()
    object LoginInputs: WelcomeUiState()
    object RegistrationInputs: WelcomeUiState()
    object ForgotPasswordInput: WelcomeUiState()
}