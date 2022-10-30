package com.plutoisnotaplanet.mortyapp.ui.main

import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile

sealed class MainUiState {
    object Initialize : MainUiState()
    data class SelfProfileUiState(val selfProfile: UserProfile) : MainUiState()
}