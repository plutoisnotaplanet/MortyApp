package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.toUiFormat
import java.util.*

data class LocationDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("dimension")
    val dimension: String? = null,
    @SerializedName("residents")
    val residents: List<String> = emptyList(),
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("created")
    val created: Date? = null
) {

    fun toModel(): Location {
        return Location(
            id ?: 0, name, type, dimension, residents, url, created.toUiFormat()
        )
    }
}