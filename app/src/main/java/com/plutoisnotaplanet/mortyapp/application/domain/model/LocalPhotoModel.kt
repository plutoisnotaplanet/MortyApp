package com.plutoisnotaplanet.mortyapp.application.domain.model

import android.net.Uri

data class LocalPhotoModel(
    val photoType: PhotoType? = null,
    val photoUri: String? = null,
    val photoPath: String? = null,
    val photoInBase64: String? = null
) {

    val onlyUri: LocalPhotoModel
        get() = LocalPhotoModel(
            photoType = PhotoType.Avatar,
            photoUri = photoUri
        )

}

enum class PhotoType {
    Avatar
}