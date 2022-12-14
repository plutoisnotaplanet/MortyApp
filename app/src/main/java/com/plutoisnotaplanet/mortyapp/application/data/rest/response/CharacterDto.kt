package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.ApiConstants
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.CharacterEntity
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.LocationEntity
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterGender
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterSpecies
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.toUiFormat
import java.util.*

data class CharacterDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("species")
    val species: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("origin")
    val origin: LocationDto? = null,
    @SerializedName("location")
    val location: LocationDto? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("episode")
    val episodes: List<String> = emptyList(),
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("created")
    val created: Date? = null
) {

    fun toDbEntity(pageId: Int): CharacterEntity {
        return CharacterEntity(
            id ?: 0,
            name,
            status.toCharacterStatus(),
            species.toCharacterSpecie(),
            type,
            gender.toCharacterGender(),
            origin?.toModel(),
            location?.toModel(),
            image,
            episodes,
            url,
            created.toUiFormat(),
            pageId
        )
    }

    fun toModel(): Character {
        return Character(
            id ?: 0,
            name,
            status.toCharacterStatus(),
            species.toCharacterSpecie(),
            type,
            gender.toCharacterGender(),
            origin?.toModel(),
            location?.toModel(),
            image,
            episodes,
            url,
            created.toUiFormat()
        )
    }

    private fun String?.toCharacterSpecie(): CharacterSpecies {
        return CharacterSpecies.values().first { it.apiValue == this }
    }

    private fun String?.toCharacterStatus(): CharacterStatus {
        return CharacterStatus.values().first { it.apiValue == this }
    }

    private fun String?.toCharacterGender(): CharacterGender {
        return CharacterGender.values().first { it.apiValue == this }
    }
}