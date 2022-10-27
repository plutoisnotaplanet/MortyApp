package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters.CharactersScreen
import com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.episodes.EpisodesScreen
import com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.locations.LocationsScreen
import com.plutoisnotaplanet.mortyapp.ui.main.HomeTabStateHolder
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen

@Composable
fun HomeScopeScreen(
    modifier: Modifier = Modifier,
    isNavigationDrawerEnabled: (Boolean) -> Unit,
) {

    val scaffoldState = rememberScaffoldState()

    val navController = rememberNavController()

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isNavigationDrawerEnabled(destination.route == NavScreen.Characters.route)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        val tabStateHolder = HomeTabStateHolder(
            rememberLazyListState(),
            rememberLazyListState(),
            rememberLazyListState(),
        )

        Box(modifier = modifier.padding(paddingValues)) {

            NavHost(navController = navController, startDestination = NavScreen.Characters.route) {
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
                        lazyListState = tabStateHolder.locationsLazyGridState
                    )
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
                        navArgument(NavScreen.CharacterDetails.argument0) {
                            type = NavType.LongType
                        }
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

                    val locationId =
                        backStackEntry.arguments?.getLong(NavScreen.LocationDetails.argument0)
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

                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
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