package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.LocationDto
import java.util.*

data class Character(
    val id: Long? = null,
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val origin: Location? = null,
    val location: Location? = null,
    val image: String? = null,
    val episodes: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
) {
}