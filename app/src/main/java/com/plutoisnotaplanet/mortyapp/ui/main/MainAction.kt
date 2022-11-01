package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseAction

sealed class MainAction: BaseAction() {
    data class OpenCamera(val uri: Uri) : MainAction()
    object OpenGalleryChooser: MainAction()
    object OpenDrawerMenu: MainAction()
}