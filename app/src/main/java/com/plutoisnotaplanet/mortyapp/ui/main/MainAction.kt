package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.annotation.StringRes

sealed class MainAction {
    object OpenCamera : MainAction()
    object OpenGalleryChooser: MainAction()
    data class ShowStringSnack(val message: String) : MainAction()
    data class ShowResourceSnack(@StringRes val message: Int) : MainAction()
}