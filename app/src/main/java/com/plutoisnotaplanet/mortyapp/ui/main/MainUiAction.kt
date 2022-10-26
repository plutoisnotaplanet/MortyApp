package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.annotation.StringRes

sealed class MainUiAction {
    object Initialize: MainUiAction()
    data class ShowSnackBarString(val message: String): MainUiAction()
    data class ShowSnackBarRes(@StringRes val message: Int): MainUiAction()
}