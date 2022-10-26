package com.plutoisnotaplanet.mortyapp.application.domain.model

import android.net.Uri

data class LocalPhotoModel(
    val photoType: PhotoType? = null,
    val photoUri: String? = null,
    val photoPath: String? = null,
    val photoInBase64: String? = null
)

enum class PhotoType {
    Avatar
}