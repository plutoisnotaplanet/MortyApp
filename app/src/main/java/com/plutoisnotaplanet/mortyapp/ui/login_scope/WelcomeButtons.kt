package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultButton
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen


@Composable
fun WelcomeButtons(
    modifier: Modifier = Modifier,
    welcomeTransition: Transition<LaunchState>,
    launchState: LaunchState,
    updateUiState: (LaunchState) -> Unit
) {

    val loginBtnPos by welcomeTransition.animateInt(transitionSpec = {
        tween(
            if (launchState == LaunchState.LoggedOut) {
                700
            } else 200, easing = EaseOut
        )
    }, label = "loginBtnPosition", targetValueByState = { state ->
        when (state) {
            LaunchState.LoggedOut -> 0
            LaunchState.LoginInputs, LaunchState.RegistrationInputs -> -500
            else -> 500
        }
    })

    val regBtnPos by welcomeTransition.animateInt(transitionSpec = {
        tween(
            if (launchState == LaunchState.LoggedOut) {
                700
            } else 200, easing = EaseOut
        )
    }, label = "regBtnPosition", targetValueByState = { state ->
        when (state) {
            LaunchState.LoggedOut -> 0
            LaunchState.LoginInputs, LaunchState.RegistrationInputs -> 500
            else -> -500
        }
    })
    Column(modifier = Modifier.fillMaxSize()) {
        DefaultButton(
            text = NavScreen.Login.route,
            onClick = { updateUiState(LaunchState.LoginInputs) },
            modifier = modifier
                .padding(bottom = 4.dp)
                .absoluteOffset(x = loginBtnPos.dp)
        )

        DefaultButton(
            text = NavScreen.Registration.route,
            onClick = { updateUiState(LaunchState.RegistrationInputs) },
            modifier = modifier.absoluteOffset(x = regBtnPos.dp)
        )
    }
}