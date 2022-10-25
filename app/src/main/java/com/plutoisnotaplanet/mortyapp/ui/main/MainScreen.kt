package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plutoisnotaplanet.mortyapp.application.domain.model.SnackBarMessage
import com.plutoisnotaplanet.mortyapp.application.utils.compose.ErrorSnackBar
import com.plutoisnotaplanet.mortyapp.ui.home_scope.HomeScopeScreen
import com.plutoisnotaplanet.mortyapp.ui.login_scope.WelcomeScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.utils.SnackbarController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarController = SnackbarController(coroutineScope)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        scaffoldState = scaffoldState,
        content = { padding ->
            Navigation(
                modifier = Modifier.padding(padding),
                navController = navController,
                viewModel = viewModel
            ) { snackMessage ->
                Timber.e("snack $snackMessage")
                snackBarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = snackMessage
                )
            }
        },
        backgroundColor = Color.White // Set background color to avoid the white flashing when you switch between screens
    )
}

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    showSnack: (String) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        composable(
            route = NavScreen.Splash.route
        ) {
            WelcomeScreen(
                navHostController = navController,
                showSnack = showSnack,
            ) { viewModel.isLogged }
        }
        composable(
            route = NavScreen.NavHomeScope.route
        ) {
            HomeScopeScreen(mainNavController = navController, viewModel = viewModel)
        }
    }
}

