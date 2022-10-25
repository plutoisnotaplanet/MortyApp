package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.SnackBarMessage
import com.plutoisnotaplanet.mortyapp.application.domain.model.asString
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import kotlinx.coroutines.*

@Immutable
enum class LaunchState {
    Initialize, LoggedIn, LoggedOut, LoginInputs, RegistrationInputs, ForgotPasswordInput
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    showSnack: (String) -> Unit,
    isLoggedIn: () -> Boolean
) {

    val launchState by viewModel.uiState

    val snackBarMessage by viewModel.snackBarMessage.collectAsState(initial = SnackBarMessage.EmptyMessage)

    showSnack(snackBarMessage.asString())

    val welcomeTransition = updateTransition(targetState = launchState, label = null)


    BackHandler {
        when (launchState) {
            LaunchState.LoginInputs, LaunchState.RegistrationInputs -> {
                viewModel.updateUiState(LaunchState.LoggedOut)
            }
            LaunchState.LoggedOut -> {
                viewModel.updateUiState(LaunchState.Initialize)
            }
            LaunchState.ForgotPasswordInput -> {
                viewModel.updateUiState(LaunchState.LoginInputs)
            }
            else -> {}
        }
    }

    val bgColor by welcomeTransition.animateColor(transitionSpec = { tween(1500) },
        label = "bgColor",
        targetValueByState = { state ->
            when (state) {
                LaunchState.LoggedIn -> colorResource(id = R.color.colorAccent)
                else -> colorResource(id = R.color.white)
            }
        })

    Column(
        modifier = modifier
            .background(color = bgColor)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                if (launchState == LaunchState.Initialize) {
                    when (isLoggedIn()) {
                        true -> viewModel.updateUiState(LaunchState.LoggedIn)
                        false -> viewModel.updateUiState(LaunchState.LoggedOut)
                    }
                }
            },
    ) {

        WelcomeLabel(welcomeTransition = welcomeTransition, navHostController = navHostController)

        when (launchState) {
            LaunchState.LoggedOut -> {
                WelcomeButtons(
                    welcomeTransition = welcomeTransition,
                    launchState = launchState,
                    updateUiState = viewModel::updateUiState
                )
            }
            LaunchState.LoginInputs, LaunchState.RegistrationInputs, LaunchState.ForgotPasswordInput -> {
                WelcomeInputs(
                    viewModel = viewModel,
                    welcomeTransition = welcomeTransition,
                    launchState = launchState,
                    forgotPasswordClick = { viewModel.updateUiState(LaunchState.ForgotPasswordInput) }
                )
            }
            else -> {}
        }
    }
}


fun navigateToHomeScope(navHostController: NavHostController, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        navHostController.navigate(
            route = NavScreen.NavHomeScope.route,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = NavScreen.Splash.route, inclusive = true
            ).build()
        )
    }
}
