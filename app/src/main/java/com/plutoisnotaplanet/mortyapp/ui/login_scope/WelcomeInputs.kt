package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultButton
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultClickableText
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultInputField
import com.plutoisnotaplanet.mortyapp.application.utils.compose.PasswordInputField


@Composable
fun WelcomeInputs(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel,
    welcomeTransition: Transition<LaunchState>,
    launchState: LaunchState,
    forgotPasswordClick: () -> Unit
) {

    val inputFieldsPos by welcomeTransition.animateInt(transitionSpec = {
        tween(
            300,
            easing = EaseOut
        )
    },
        label = "inputFieldsPos",
        targetValueByState = { state ->
            when (state) {
                LaunchState.LoginInputs, LaunchState.RegistrationInputs, LaunchState.ForgotPasswordInput -> 0
                else -> -400
            }
        })

    val loginInputPos by welcomeTransition.animateInt(transitionSpec = {
        tween(300, easing = EaseOut)
    },
        label = "loginInputPos",
        targetValueByState = { state ->
            when (state) {
                LaunchState.ForgotPasswordInput -> -200
                else -> 0
            }
        }
    )

    val buttonPos by welcomeTransition.animateInt(
        transitionSpec = {
            tween(300, easing = EaseOut)
        },
        label = "buttonApplyPos",
        targetValueByState = { state ->
            when (state) {
                LaunchState.ForgotPasswordInput -> -120
                else -> 200
            }
        })


    val loginErrorState by viewModel.loginErrorState.collectAsState(initial = InputState.Initialize)
    val passwordErrorState by viewModel.passwordErrorState.collectAsState(initial = InputState.Initialize)

    val isApplyButtonEnabled = viewModel.isApplyButtonEnabled(loginErrorState, passwordErrorState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .absoluteOffset(x = inputFieldsPos.dp)
    ) {

        DefaultInputField(
            value = viewModel.loginInput,
            onValueChange = viewModel::updateLogin,
            label = stringResource(R.string.tt_email),
            modifier = modifier.absoluteOffset(y = loginInputPos.dp),
            inputState = loginErrorState
        )

        AnimatedVisibility(
            visible = launchState == LaunchState.LoginInputs,
            enter = fadeIn(animationSpec = tween(durationMillis = 50,  easing = EaseOut)),
            exit = fadeOut(animationSpec = tween(durationMillis = 50, easing = EaseOut))
        ) {
            DefaultClickableText(
                modifier = modifier
                    .absoluteOffset(y = 84.dp, x = 224.dp),
                title = stringResource(id = R.string.tv_forgot_password)
            ) {
                forgotPasswordClick()
            }
        }

        AnimatedVisibility(
            visible = launchState != LaunchState.ForgotPasswordInput,
            enter = fadeIn(animationSpec = tween(durationMillis = 50,  easing = EaseOut)),
            exit = fadeOut(animationSpec = tween(durationMillis = 50, easing = EaseOut))
        ) {
            PasswordInputField(
                value = viewModel.passwordInput,
                onValueChange = viewModel::updatePassword,
                label = stringResource(R.string.tt_password),
                modifier = modifier.absoluteOffset(y = 100.dp + if (launchState == LaunchState.LoginInputs) 16.dp else 0.dp),
                inputState = passwordErrorState
            )
        }

        DefaultButton(text = stringResource(R.string.tv_apply),
            modifier = modifier
                .padding(top = 16.dp)
                .absoluteOffset(y = buttonPos.dp),
            enabled = if (launchState != LaunchState.ForgotPasswordInput) isApplyButtonEnabled else loginErrorState is InputState.Valid,
            onClick = {
                when(launchState) {
                    LaunchState.LoginInputs -> viewModel.signIn()
                    LaunchState.RegistrationInputs -> viewModel.signUp()
                    LaunchState.ForgotPasswordInput -> viewModel.resetPassword()
                    else -> {}
                }
            })

    }
}