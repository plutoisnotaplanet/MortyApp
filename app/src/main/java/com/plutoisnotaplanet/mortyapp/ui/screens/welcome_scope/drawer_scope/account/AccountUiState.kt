package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope.drawer_scope.account

import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.common.base.UiState

sealed class AccountUiState: UiState() {
    object Initialize: AccountUiState()
    data class SelfProfileUiState(val userProfile: UserProfile): AccountUiState()
}