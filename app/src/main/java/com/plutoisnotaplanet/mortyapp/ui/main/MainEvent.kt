package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.annotation.StringRes

sealed class MainEvent {
    object OpenCamera : MainEvent()
    object OpenGalleryChooser: MainEvent()
    object OpenDrawerMenu: MainEvent()
    data class ShowStringSnack(val message: String) : MainEvent()
    data class ShowResourceSnack(@StringRes val message: Int) : MainEvent()
}