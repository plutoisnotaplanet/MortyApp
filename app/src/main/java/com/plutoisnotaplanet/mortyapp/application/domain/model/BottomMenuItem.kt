package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrowseGallery
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomMenuAction(val value: String, val image: ImageVector) {
    OpenCamera("Open camera", Icons.Filled.Camera),
    OpenGallery("Open gallery", Icons.Filled.PhotoAlbum),
    DeletePhoto("Delete photo", Icons.Filled.Delete)
}