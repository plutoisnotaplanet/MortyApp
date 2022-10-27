package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseUiViewState

sealed class AccountUiViewState: BaseUiViewState() {
    data class SelfProfileViewState(val userProfile: UserProfile): AccountUiViewState()
}