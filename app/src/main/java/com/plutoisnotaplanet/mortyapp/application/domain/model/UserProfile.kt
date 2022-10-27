package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.runtime.Immutable
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character

@Immutable
data class UserProfile(
    val email: String? = null,
    val photoData: LocalPhotoModel? = null,
    val favoriteCharactersList: List<Character>? = null,
    val favoriteEpisodesList: List<Location>? = null,
    val countOfLocalCharacters: Int = 0,
    val countOfLocalLocations: Int = 0,
    val countOfLocalEpisodes: Int = 0
) {

    val isEmpty: Boolean
        get() = favoriteCharactersList.isNullOrEmpty() && favoriteEpisodesList.isNullOrEmpty() && countOfLocalCharacters == 0 &&
                countOfLocalEpisodes == 0 && countOfLocalLocations == 0

    val isNotEmpty: Boolean
        get() = !isEmpty
}