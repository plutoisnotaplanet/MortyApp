package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen


@Composable
fun WelcomeLabel(
    modifier: Modifier = Modifier,
    welcomeTransition: Transition<WelcomeUiState>,
    navHostController: NavHostController
) {

    var isNavigated by remember {
        mutableStateOf(false)
    }

    val labelYPosition by welcomeTransition.animateInt(transitionSpec = {
        tween(
            700,
            easing = EaseOut
        )
    },
        label = "labelYTransition",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.Initialize -> 0
                WelcomeUiState.LoginInputs, WelcomeUiState.RegistrationInputs -> -10
                else -> -20
            }
        })

    val labelSize by welcomeTransition.animateFloat(transitionSpec = {
        tween(
            700,
            easing = EaseOut
        )
    },
        label = "labelSize",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.Initialize -> 1f
                WelcomeUiState.LoginInputs, WelcomeUiState.RegistrationInputs -> 0.5f
                else -> 0.7f
            }
        })

    val zoomLoggedInTransition by welcomeTransition.animateFloat(
        transitionSpec = { tween(durationMillis = 700, easing = EaseOut) },
        label = "bgSize",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.LoggedIn -> 128f
                else -> 1f
            }
        },
    )

    val loggedInLabelRotate by welcomeTransition.animateFloat(transitionSpec = {
        tween(
            durationMillis = 700,
            easing = EaseOut
        )
    },
        label = "loggedInLabelRotate",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.LoggedIn -> 512f
                else -> 0f
            }
        })

    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = modifier
                .graphicsLayer(
                    scaleY = zoomLoggedInTransition,
                    scaleX = zoomLoggedInTransition,
                    rotationZ = loggedInLabelRotate
                )
                .absoluteOffset(y = labelYPosition.dp)
                .fillMaxSize(fraction = labelSize)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null
        )
    }

    if (welcomeTransition.currentState == WelcomeUiState.LoggedIn && !isNavigated) {
        navHostController.navigate(
            route = NavScreen.NavHomeScope.route,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = NavScreen.Splash.route, inclusive = true
            ).build()
        )
        isNavigated = true
    }
}
