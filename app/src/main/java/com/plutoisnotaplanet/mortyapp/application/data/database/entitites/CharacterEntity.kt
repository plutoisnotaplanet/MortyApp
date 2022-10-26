package com.plutoisnotaplanet.mortyapp.application.data.database.entitites

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterGender
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterSpecies
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location

@Entity(tableName = MortyDataBase.CHARACTERS_DB)
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
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
    val page: Int = 0,
    var isFavorite: Boolean = false
) {

    fun toModel(): Character {
        return Character(
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
            created,
            isFavorite
        )
    }
}