package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.LocationDto
import java.util.*

data class Character(
    val id: Long = 0,
    val name: String? = "Unknown",
    val status: CharacterStatus = CharacterStatus.Unknown,
    val species: String? = null,
    val type: String? = null,
    val gender: CharacterGender = CharacterGender.Unknown,
    val origin: Location? = null,
    val location: Location? = null,
    val image: String? = null,
    val episodes: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
) {
}