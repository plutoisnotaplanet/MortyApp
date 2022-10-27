package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseUiViewState
import com.plutoisnotaplanet.mortyapp.ui.components.DefaultButton
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen


@Composable
fun WelcomeButtons(
    modifier: Modifier = Modifier,
    welcomeTransition: Transition<BaseUiViewState>,
    launchState: BaseUiViewState,
    updateUiState: (LaunchViewState) -> Unit
) {

    val loginBtnPos by welcomeTransition.animateInt(transitionSpec = {
        tween(
            if (launchState == LaunchViewState.LoggedOut) {
                700
            } else 200, easing = EaseOut
        )
    }, label = "loginBtnPosition", targetValueByState = { state ->
        when (state) {
            LaunchViewState.LoggedOut -> 0
            LaunchViewState.LoginInputs, LaunchViewState.RegistrationInputs -> -500
            else -> 500
        }
    })

    val regBtnPos by welcomeTransition.animateInt(transitionSpec = {
        tween(
            if (launchState == LaunchViewState.LoggedOut) {
                700
            } else 200, easing = EaseOut
        )
    }, label = "regBtnPosition", targetValueByState = { state ->
        when (state) {
            LaunchViewState.LoggedOut -> 0
            LaunchViewState.LoginInputs, LaunchViewState.RegistrationInputs -> 500
            else -> -500
        }
    })
    Column(modifier = Modifier.fillMaxSize()) {
        DefaultButton(
            text = NavScreen.Login.route,
            onClick = { updateUiState(LaunchViewState.LoginInputs) },
            modifier = modifier
                .padding(bottom = 4.dp)
                .absoluteOffset(x = loginBtnPos.dp)
        )

        DefaultButton(
            text = NavScreen.Registration.route,
            onClick = { updateUiState(LaunchViewState.RegistrationInputs) },
            modifier = modifier.absoluteOffset(x = regBtnPos.dp)
        )
    }
}