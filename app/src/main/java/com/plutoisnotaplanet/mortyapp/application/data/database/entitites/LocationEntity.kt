package com.plutoisnotaplanet.mortyapp.application.data.database.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location

@Entity(tableName = MortyDataBase.LOCATIONS_DB)
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null,
    val page: Int = 0
) {

    fun toModel(): Location {
        return Location(
            id, name, type, dimension, residents, url, created
        )
    }
}