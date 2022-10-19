package com.plutoisnotaplanet.mortyapp.ui.main

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.CharactersScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.episodes.EpisodesScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.locations.LocationsScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import timber.log.Timber

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(
                    navController = navController
                )
            }
        },
        backgroundColor = colorResource(R.color.black) // Set background color to avoid the white flashing when you switch between screens
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState(),
    )
    NavHost(
        navController = navController,
        startDestination = NavScreen.Characters.route
    ) {
        composable(
            route = NavScreen.Characters.route
        ) {
            CharactersScreen(
                viewModel = hiltViewModel(),
                lazyListState = tabStateHolder.charactersLazyListState
            ) { characterId ->
                navController.navigate("${NavScreen.CharacterDetails.route}/$characterId")
            }
        }
        composable(
            route = NavScreen.Locations.route
        ) {
            LocationsScreen(
                viewModel = hiltViewModel(),
                lazyListState = tabStateHolder.locationsLazyListState
            ) { locationId ->
                navController.navigate("${NavScreen.LocationDetails.route}/$locationId")
            }
        }
        composable(
            route = NavScreen.Episodes.route
        ) {
            EpisodesScreen(
                viewModel = hiltViewModel(),
                lazyListState = tabStateHolder.episodesLazyListState
            ) { episodeId ->
                navController.navigate("${NavScreen.EpisodeDetails.route}/$episodeId")
            }
        }
        composable(
            route = NavScreen.CharacterDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.CharacterDetails.argument0) { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val characterId =
                backStackEntry.arguments?.getLong(NavScreen.CharacterDetails.argument0)
                    ?: return@composable

//                MovieDetailScreen(posterId, hiltViewModel()) {
//                    navController.navigateUp()
//                }
        }
        composable(
            route = NavScreen.LocationDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.LocationDetails.argument0) { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val locationId = backStackEntry.arguments?.getLong(NavScreen.LocationDetails.argument0)
                ?: return@composable

//                TvDetailScreen(posterId, hiltViewModel()) {
//                    navController.navigateUp()
//                }
        }
        composable(
            route = NavScreen.EpisodeDetails.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.EpisodeDetails.argument0) { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val episodeId =
                backStackEntry.arguments?.getLong(NavScreen.EpisodeDetails.argument0)
                    ?: return@composable

//                PersonDetailScreen(personId, hiltViewModel()) {
//                    navController.navigateUp()
//                }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = MainScreenHomeTab.values()
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = colorResource(id = R.color.colorPrimaryDark)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = { Text(text = stringResource(id = item.title)) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Immutable
enum class MainScreenHomeTab(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    CHARACTERS(
        R.string.menu_characters,
        Icons.Filled.Person,
        NavScreen.Characters.route
    ),
    LOCATIONS(
        R.string.menu_locations,
        Icons.Filled.LocationOn,
        NavScreen.Locations.route
    ),
    EPISODES(
        R.string.menu_episodes,
        Icons.Filled.PlayArrow,
        NavScreen.Episodes.route
    )
}