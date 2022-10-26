package com.plutoisnotaplanet.mortyapp.ui.main

sealed class MainAction {
    object OpenCamera : MainAction()
    object OpenGalleryChooser: MainAction()

}