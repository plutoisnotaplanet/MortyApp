package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.Constants
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import com.plutoisnotaplanet.mortyapp.ui.theme.Purple200

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
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
    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(
            route = NavScreen.Home.route,
            arguments = emptyList()
        ) {
            HomeTabScreen(
                viewModel = hiltViewModel(),
                tabStateHolder = tabStateHolder,
                selectItem = { tab, index ->
                    when (tab) {
                        MainScreenHomeTab.CHARACTERS -> navController.navigate("${NavScreen.CharacterDetails.route}/$index")
                        MainScreenHomeTab.LOCATIONS -> navController.navigate("${NavScreen.LocationDetails.route}/$index")
                        MainScreenHomeTab.EPISODES -> navController.navigate("${NavScreen.EpisodeDetails.route}/$index")
                    }
                }
            )
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
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = Purple200,
        contentColor = Color.White
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = MainScreenHomeTab.values()
    BottomNavigation(
        backgroundColor = Color.Cyan,
        contentColor = Color.White
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
        Constants.BottomNavBarRoutes.Characters
    ),
    LOCATIONS(
        R.string.menu_locations,
        Icons.Filled.LocationOn,
        Constants.BottomNavBarRoutes.Locations
    ),
    EPISODES(R.string.menu_episodes, Icons.Filled.PlayArrow, Constants.BottomNavBarRoutes.Episodes);
}