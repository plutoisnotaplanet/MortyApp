package com.plutoisnotaplanet.mortyapp.ui.main

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.plutoisnotaplanet.mortyapp.ui.home_scope.HomeScopeScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.CharactersScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.episodes.EpisodesScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.locations.LocationsScreen
import com.plutoisnotaplanet.mortyapp.ui.login_scope.SplashScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.DrawerContent
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()

//    Scaffold(
//        content = { padding ->
//
//        },
//        backgroundColor = Color.White // Set background color to avoid the white flashing when you switch between screens
//    )
    Box(modifier = Modifier.background(Color.White)) {
        Navigation(
            navController = navController,
            isLogged = viewModel.isLogged
        )
    }
}

@Composable
fun Navigation(isLogged: Boolean, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        composable(
            route = NavScreen.Splash.route
        ) {
            SplashScreen(
                navHostController = navController
            ) { isLogged }
        }

        composable(
            route = NavScreen.Login.route
        ) {

        }
        composable(
            route = NavScreen.Registration.route
        ) {

        }
        composable(
            route = NavScreen.NavHomeScope.route
        ) {
            HomeScopeScreen()
        }
    }
}