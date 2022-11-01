package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.extensions.prepareSnackBars
import com.plutoisnotaplanet.mortyapp.ui.main.MainEvent


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onMainEvent: (MainEvent) -> Unit,
    isLoggedIn: () -> Boolean
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.singleAction.prepareSnackBars(onMainEvent)
    }

    val uiState by viewModel.uiState

    val welcomeTransition = updateTransition(targetState = uiState, label = null)

    val dispatcher: OnBackPressedDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    BackHandler {
        when (uiState) {
            WelcomeUiState.LoginInputs, WelcomeUiState.RegistrationInputs -> {
                viewModel.updateUiState(WelcomeUiState.LoggedOut)
            }
            WelcomeUiState.LoggedOut -> {
                viewModel.updateUiState(WelcomeUiState.Initialize)
            }
            WelcomeUiState.ForgotPasswordInput -> {
                viewModel.updateUiState(WelcomeUiState.LoginInputs)
            }
            else -> dispatcher.onBackPressed()
        }
        viewModel.clearInputs()
    }

    val bgColor by welcomeTransition.animateColor(transitionSpec = { tween(1500) },
        label = "bgColor",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.LoggedIn -> colorResource(id = R.color.colorAccent)
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
                interactionSource = MutableInteractionSource(),
                onClick = {
                    if (uiState == WelcomeUiState.Initialize) {
                        when (isLoggedIn()) {
                            true -> viewModel.updateUiState(WelcomeUiState.LoggedIn)
                            false -> viewModel.updateUiState(WelcomeUiState.LoggedOut)
                        }
                    }
                }
            ),
    ) {

        WelcomeLabel(welcomeTransition = welcomeTransition, navHostController = navHostController)

        when (uiState) {
            WelcomeUiState.LoggedOut -> {
                WelcomeButtons(
                    welcomeTransition = welcomeTransition,
                    launchState = uiState,
                    updateUiState = viewModel::updateUiState
                )
            }
            WelcomeUiState.LoginInputs, WelcomeUiState.RegistrationInputs, WelcomeUiState.ForgotPasswordInput -> {
                WelcomeInputs(
                    viewModel = viewModel,
                    welcomeTransition = welcomeTransition,
                    launchState = uiState,
                    forgotPasswordClick = { viewModel.updateUiState(WelcomeUiState.ForgotPasswordInput) }
                )
            }
            else -> {}
        }
    }
}
