package com.plutoisnotaplanet.mortyapp.ui.main

import android.os.Bundle
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseAction
import com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope.drawer_scope.account.AccountScreen
import com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account.SettingsScreen
import com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.HomeScopeScreen
import com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope.WelcomeScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.DrawerContent
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavigationDrawerItem
import com.plutoisnotaplanet.mortyapp.ui.common.SnackbarController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    var selfProfile by remember {
        mutableStateOf(UserProfile())
    }

    when(uiState) {
        is MainUiState.SelfProfileUiState -> {
            selfProfile = (uiState as MainUiState.SelfProfileUiState).selfProfile
        }
        else -> {}
    }

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val snackBarController = SnackbarController(this)

        viewModel.singleAction.collectLatest { uiEvent ->
            when (uiEvent) {
                is BaseAction.ShowStringSnack -> {
                    snackBarController.showSnackbar(
                        scaffoldState,
                        uiEvent.message
                    )
                }
                is BaseAction.ShowResourceSnack -> {
                    snackBarController.showSnackbar(
                        scaffoldState,
                        context.getString(uiEvent.message)
                    )
                }
                is MainAction.OpenDrawerMenu -> {
                    scaffoldState.drawerState.open()
                }
                else -> {}
            }
        }
    }

    //TODO: add animation on Drawer Opening
//
//    val drawerOffset = scaffoldState.drawerState.offset
//
//    val mainTransition =
//        updateTransition(targetState = drawerOffset.value, label = "mainTransition")
//
//    val scale = 1.0207939f
//
//    val scaleScaffold by mainTransition.animateFloat(
//        transitionSpec = { tween(durationMillis = 10) },
//        label = "scaffoldScale",
//        targetValueByState = { offset ->
//            val current = -offset / 1058
//            val diff = (scale - current)
//            val result = scale - diff + 1f
////            Timber.e("result: ${result} scale $scale current $current diff $diff")
//            result
//        }
//    )
//
//    val xEnd = 36f
//
//    val scaffoldXPos by mainTransition.animateFloat(
//        transitionSpec = { tween(durationMillis = 10) },
//        label = "scaffoldXPos",
//        targetValueByState = { offset ->
//            val current = -offset / 30
//            xEnd - current
//        }
//    )
//
//    val yEnd = -36f
//
//    val scaffoldYPos by mainTransition.animateFloat(
//        transitionSpec = { tween(durationMillis = 10) },
//        label = "scaffoldYPos",
//        targetValueByState = { offset ->
//            val current = offset / 30
//            yEnd - current
//        }
//    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState.snackbarHostState) },
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerShape = RoundedCornerShape(0.dp),
        drawerContent = {
            DrawerContent(selfProfile) { route ->
                when (route) {
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
                modifier = Modifier
                    .padding(padding),
                navController = navController,
                viewModel = viewModel,
                onMainEvent = viewModel::obtainEvent
            )
        },
        backgroundColor = Color.White // Set background color to avoid the white flashing when you switch between screens
    )
}

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MainViewModel,
    onMainEvent: (MainEvent) -> Unit
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
                onMainEvent = onMainEvent,
                isLoggedIn = { viewModel.isLogged() }
            )
        }
        composable(
            route = NavScreen.NavHomeScope.route
        ) {
            HomeScopeScreen(onMainEvent = onMainEvent)
            viewModel.loadDrawerProfile()
        }

        composable(
            route = NavScreen.Account.route
        ) {
            AccountScreen(onMainEvent = onMainEvent)
        }

        composable(
            route = NavScreen.Settings.route
        ) {
            SettingsScreen()
        }
    }
}

