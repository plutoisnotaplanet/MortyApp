package com.plutoisnotaplanet.mortyapp.application.data.rest.response

import com.google.gson.annotations.SerializedName
import com.plutoisnotaplanet.mortyapp.application.domain.model.Episode
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.toUiFormat
import java.util.*

data class EpisodeDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("air_date")
    val airDate: String? = null,
    @SerializedName("episode")
    val episode: String? = null,
    @SerializedName("characters")
    val characters: List<String> = emptyList(),
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("created")
    val created: Date? = null
) {

    fun toModel(): Episode {
        return Episode(
            id, name, airDate, episode, characters, url, created.toUiFormat()
        )
    }
}