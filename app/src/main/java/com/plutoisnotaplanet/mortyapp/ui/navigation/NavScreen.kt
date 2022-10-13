package com.plutoisnotaplanet.mortyapp.ui.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object CharacterDetails : NavScreen("CharacterDetails") {

        const val routeWithArgument: String = "CharacterDetails/{characterId}"

        const val argument0: String = "characterId"
    }

    object LocationDetails : NavScreen("LocationDetails") {

        const val routeWithArgument: String = "LocationDetails/{locationId}"

        const val argument0: String = "locationId"
    }

    object EpisodeDetails : NavScreen("EpisodeDetails") {

        const val routeWithArgument: String = "EpisodeDetails/{episodeId}"

        const val argument0: String = "episodeId"
    }
}