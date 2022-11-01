package com.plutoisnotaplanet.mortyapp.ui.main

import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.common.base.UiState

sealed class MainUiState: UiState() {
    object Initialize: MainUiState()
    data class SelfProfileUiState(val selfProfile: UserProfile) : MainUiState()
}