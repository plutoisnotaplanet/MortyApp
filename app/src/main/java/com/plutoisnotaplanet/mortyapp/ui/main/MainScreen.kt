package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.drawer_scope.AccountScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.HomeScopeScreen
import com.plutoisnotaplanet.mortyapp.ui.login_scope.WelcomeScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.DrawerContent
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavigationDrawerItem
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.utils.SnackbarController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val uiEvent by viewModel.uiEventFlow.collectAsState(initial = MainUiAction.Initialize)

    val selfProfile by viewModel.selfProfile

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackBarController = SnackbarController(coroutineScope)

    when(uiEvent) {
        is MainUiAction.ShowSnackBarString -> {
            snackBarController.showSnackbar(scaffoldState, (uiEvent as MainUiAction.ShowSnackBarString).message)
        }
        else -> {}
    }

    val isNavigationDrawerEnabled = remember {
        mutableStateOf(false)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = isNavigationDrawerEnabled.value,
        drawerContent = {
            DrawerContent(selfProfile) { route ->
                when(route) {
                    NavigationDrawerItem.LOGOUT.route -> {
                        viewModel.logout()
                        coroutineScope.launch {
                            navController.navigate(
                                route = route,
                                navOptions = NavOptions.Builder().setPopUpTo(
                                    route = NavScreen.NavHomeScope.route, inclusive = true
                                ).build()
                            )
                            delay(150)
                            scaffoldState.drawerState.close()
                        }
                    }
                    else -> {
                        if (navController.currentDestination?.route != route) {
                            navController.navigate(route)
                        }
                        coroutineScope.launch {
                            delay(150)
                            scaffoldState.drawerState.close()
                        }
                    }
                }
            }
        },
        content = { padding ->
            Navigation(
                modifier = Modifier.padding(padding),
                navController = navController,
                viewModel = viewModel,
                isNavigationDrawerEnabled = { isEnabled ->
                Timber.e("isNav $isEnabled")
                    isNavigationDrawerEnabled.value = isEnabled },
            ) { snackMessage ->
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
    isNavigationDrawerEnabled: (Boolean) -> Unit,
    showSnack: (String) -> Unit,
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
            HomeScopeScreen(isNavigationDrawerEnabled = isNavigationDrawerEnabled)
        }

        composable(
            route = NavScreen.Account.route
        ) {
            AccountScreen { mainAction ->
                viewModel.handleAction(mainAction)
            }
        }
    }
}

