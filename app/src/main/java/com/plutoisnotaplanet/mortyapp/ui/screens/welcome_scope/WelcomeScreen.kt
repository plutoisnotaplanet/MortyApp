package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

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
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseUiViewState
import com.plutoisnotaplanet.mortyapp.ui.common.base.loadSnackBar
import com.plutoisnotaplanet.mortyapp.ui.main.MainAction
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import kotlinx.coroutines.*

@Immutable
sealed class LaunchViewState: BaseUiViewState() {
    object LoggedIn: LaunchViewState()
    object LoggedOut: LaunchViewState()
    object LoginInputs: LaunchViewState()
    object RegistrationInputs: LaunchViewState()
    object ForgotPasswordInput: LaunchViewState()
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onMainAction: (MainAction) -> Unit,
    isLoggedIn: () -> Boolean
) {

    val uiState by viewModel.uiState
    uiState.loadSnackBar(onMainAction)

    val welcomeTransition = updateTransition(targetState = uiState, label = null)

    BackHandler {
        when (uiState) {
            LaunchViewState.LoginInputs, LaunchViewState.RegistrationInputs -> {
                viewModel.updateUiState(LaunchViewState.LoggedOut)
            }
            LaunchViewState.LoggedOut -> {
                viewModel.updateUiState(BaseUiViewState.Initialize)
            }
            LaunchViewState.ForgotPasswordInput -> {
                viewModel.updateUiState(LaunchViewState.LoginInputs)
            }
            else -> {}
        }
    }

    val bgColor by welcomeTransition.animateColor(transitionSpec = { tween(1500) },
        label = "bgColor",
        targetValueByState = { state ->
            when (state) {
                LaunchViewState.LoggedIn -> colorResource(id = R.color.colorAccent)
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
                if (uiState == BaseUiViewState.Initialize) {
                    when (isLoggedIn()) {
                        true -> viewModel.updateUiState(LaunchViewState.LoggedIn)
                        false -> viewModel.updateUiState(LaunchViewState.LoggedOut)
                    }
                }
            },
    ) {

        WelcomeLabel(welcomeTransition = welcomeTransition, navHostController = navHostController)

        when (uiState) {
            LaunchViewState.LoggedOut -> {
                WelcomeButtons(
                    welcomeTransition = welcomeTransition,
                    launchState = uiState,
                    updateUiState = viewModel::updateUiState
                )
            }
            LaunchViewState.LoginInputs, LaunchViewState.RegistrationInputs, LaunchViewState.ForgotPasswordInput -> {
                WelcomeInputs(
                    viewModel = viewModel,
                    welcomeTransition = welcomeTransition,
                    launchState = uiState,
                    forgotPasswordClick = { viewModel.updateUiState(LaunchViewState.ForgotPasswordInput) }
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
