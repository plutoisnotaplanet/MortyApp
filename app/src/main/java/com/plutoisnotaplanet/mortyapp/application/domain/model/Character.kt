package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.CharacterEntity
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.LocationDto
import java.util.*

data class Character(
    val id: Long = 0,
    val name: String? = "Unknown",
    val status: CharacterStatus = CharacterStatus.Unknown,
    val species: CharacterSpecies = CharacterSpecies.Unknown,
    val type: String? = null,
    val gender: CharacterGender = CharacterGender.Unknown,
    val origin: Location? = null,
    val location: Location? = null,
    val image: String? = null,
    val episodes: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null,
    var isFavorite: Boolean = false
) {

    fun toDbEntity(): CharacterEntity {
        return CharacterEntity(
            id,
            name,
            status,
            species,
            type,
            gender,
            origin,
            location,
            image,
            episodes,
            url,
            created
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Character) return false
        if (other.id != id) return false
        return true
    }
}