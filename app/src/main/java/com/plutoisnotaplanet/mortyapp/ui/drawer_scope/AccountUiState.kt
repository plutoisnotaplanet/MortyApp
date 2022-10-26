package com.plutoisnotaplanet.mortyapp.ui.drawer_scope

import android.net.Uri

sealed class AccountUiState {

    object Initialize : AccountUiState()

    data class OpenCameraState(val uri: Uri): AccountUiState()
}