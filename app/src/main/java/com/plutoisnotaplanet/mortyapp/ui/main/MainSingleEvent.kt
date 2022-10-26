package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri

sealed class MainSingleEvent {
    data class OpenCamera(val uri: Uri): MainSingleEvent()
    object OpenGalleryChooser: MainSingleEvent()
}