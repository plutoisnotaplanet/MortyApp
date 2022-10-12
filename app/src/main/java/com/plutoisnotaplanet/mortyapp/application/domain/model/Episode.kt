package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Episode(
    val id: Long? = null,
    val name: String? = null,
    val airDate: String? = null,
    val episode: String? = null,
    val characters: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
) {
}