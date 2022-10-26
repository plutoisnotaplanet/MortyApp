package com.plutoisnotaplanet.mortyapp.application.domain.model

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.LocationEntity
import java.util.*

data class Location(
    val id: Long = 0,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null
) {

    fun toDbEntity(): LocationEntity {
        return LocationEntity(
            id, name, type, dimension, residents, url, created
        )
    }
}