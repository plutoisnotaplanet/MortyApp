package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.InputState
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultClickableText
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultInputField
import com.plutoisnotaplanet.mortyapp.application.utils.compose.PasswordInputField
import com.plutoisnotaplanet.mortyapp.ui.components.AnimatedButton
import kotlinx.coroutines.Dispatchers


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WelcomeInputs(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel,
    welcomeTransition: Transition<WelcomeUiState>,
    launchState: WelcomeUiState,
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
                WelcomeUiState.LoginInputs,
                WelcomeUiState.RegistrationInputs,
                WelcomeUiState.ForgotPasswordInput -> 0
                else -> -400
            }
        })

    val loginInputPos by welcomeTransition.animateInt(transitionSpec = {
        tween(300, easing = EaseOut)
    },
        label = "loginInputPos",
        targetValueByState = { state ->
            when (state) {
                WelcomeUiState.ForgotPasswordInput -> -200
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
                WelcomeUiState.ForgotPasswordInput -> -120
                else -> 200
            }
        })

    val loginErrorState by viewModel.loginErrorState.collectAsState(
        initial = InputState.Initialize,
        context = Dispatchers.IO
    )
    val passwordErrorState by viewModel.passwordErrorState.collectAsState(
        initial = InputState.Initialize,
        context = Dispatchers.IO
    )

    val isApplyButtonEnabled = viewModel.isApplyEnabled(loginErrorState, passwordErrorState)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

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
            onNextAction = { focusManager.moveFocus(FocusDirection.Down) },
            inputState = loginErrorState
        )

        AnimatedVisibility(
            visible = launchState == WelcomeUiState.LoginInputs,
            enter = fadeIn(animationSpec = tween(durationMillis = 50, easing = EaseOut)),
            exit = fadeOut(animationSpec = tween(durationMillis = 50, easing = EaseOut))
        ) {
            DefaultClickableText(
                modifier = modifier
                    .absoluteOffset(y = 84.dp, x = 224.dp),
                title = stringResource(id = R.string.tv_forgot_password),
                onClick = { forgotPasswordClick() }
            )
        }

        AnimatedVisibility(
            visible = launchState != WelcomeUiState.ForgotPasswordInput,
            enter = fadeIn(animationSpec = tween(durationMillis = 50, easing = EaseOut)),
            exit = fadeOut(animationSpec = tween(durationMillis = 50, easing = EaseOut))
        ) {
            PasswordInputField(
                value = viewModel.passwordInput,
                onValueChange = viewModel::updatePassword,
                label = stringResource(R.string.tt_password),
                modifier = modifier.absoluteOffset(y = 112.dp),
                onDoneAction = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    viewModel.onApplyAction()
                },
                inputState = passwordErrorState
            )
        }

        val buttonPosition = if (launchState != WelcomeUiState.ForgotPasswordInput && passwordErrorState is InputState.Error) {
            buttonPos + 60
        } else {
            buttonPos
        }

        AnimatedButton(text = stringResource(R.string.tv_apply),
            modifier = modifier
                .padding(top = 16.dp)
                .absoluteOffset(y = buttonPosition.dp),
            enabled = isApplyButtonEnabled,
            onClick = { viewModel.onApplyAction() })
    }
}