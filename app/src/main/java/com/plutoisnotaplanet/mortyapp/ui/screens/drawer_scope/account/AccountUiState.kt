package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile

sealed class AccountUiState {
    object Initialize: AccountUiState()
    data class SelfProfileUiState(val userProfile: UserProfile): AccountUiState()
}