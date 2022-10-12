package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Location(
    val id: Long? = null,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
) {
}