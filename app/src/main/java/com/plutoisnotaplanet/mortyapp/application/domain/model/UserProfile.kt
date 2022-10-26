package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.compose.runtime.Immutable
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character

@Immutable
data class UserProfile(
    val email: String? = null,
    val photoData: LocalPhotoModel? = null,
    val favoriteCharactersList: List<Character>? = null,
    val favoriteEpisodesList: List<Location>? = null
) {
}