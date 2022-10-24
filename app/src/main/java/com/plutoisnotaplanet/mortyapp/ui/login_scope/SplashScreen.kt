package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.utils.DefaultButton
import com.plutoisnotaplanet.mortyapp.ui.navigation.NavScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class LaunchState {
    Initialize, LoggedIn, Logout
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    getLogState: () -> Boolean,
) {
    val isLogged = remember {
        mutableStateOf(LaunchState.Initialize)
    }

    val infiniteTransition = rememberInfiniteTransition()

    val welcomeTransition = updateTransition(targetState = isLogged.value, label = null)

    val coroutineScope = rememberCoroutineScope()

    val loginBtnPos by welcomeTransition.animateInt(
        transitionSpec = { tween(2000) },
        label = "loginBtnPosition",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> 250
                LaunchState.Logout -> 0
                LaunchState.LoggedIn -> 250
            }
        }
    )

    val regBtnPos by welcomeTransition.animateInt(
        transitionSpec = { tween(2000) },
        label = "regBtnPosition",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> -250
                LaunchState.Logout -> 0
                LaunchState.LoggedIn -> -250
            }
        }
    )

    val labelYPosition by welcomeTransition.animateInt(
        transitionSpec = { tween(2000) },
        label = "labelYTransition",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> 0
                LaunchState.Logout -> -200
                LaunchState.LoggedIn -> 0
            }
        }
    )

    val labelXPosition by welcomeTransition.animateInt(
        transitionSpec = { tween(2000) },
        label = "labelXTransition",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> 0
                LaunchState.Logout -> 0
                LaunchState.LoggedIn -> -550
            }
        }
    )

    val labelSize by welcomeTransition.animateFloat(
        transitionSpec = { tween(2000) },
        label = "labelSize",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> 1f
                LaunchState.Logout -> 0.7f
                LaunchState.LoggedIn -> 2f
            }
        }
    )

    val bgColor by infiniteTransition.animateColor(
        initialValue = colorResource(id = R.color.colorPrimary),
        targetValue = colorResource(id = R.color.white),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val zoomLoggedInTransition by welcomeTransition.animateFloat(
        transitionSpec = { tween(durationMillis = 2500, easing = LinearOutSlowInEasing) },
        label = "bgSize",
        targetValueByState = { state ->
            when (state) {
                LaunchState.Initialize -> 1f
                LaunchState.Logout -> 1f
                LaunchState.LoggedIn -> 25f
            }
        },
    )

    val loggedInLabelRotate by welcomeTransition.animateFloat(
        transitionSpec = { tween(durationMillis = 1500, easing = FastOutSlowInEasing) },
        label = "loggedInLabelRotate",
        targetValueByState = { state ->
            when (state) {
                LaunchState.LoggedIn -> 24f
                else -> 0f
            }
        }
    )
    ConstraintLayout(
        modifier = modifier
            .background(color = bgColor)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clickable(enabled = true) {
                when (getLogState()) {
                    true -> isLogged.value = LaunchState.LoggedIn
                    false -> {
                        coroutineScope.launch {
                            delay(2000)
                            navHostController.navigate(NavScreen.NavHomeScope.route) {
                                popUpTo(popUpToId)
                            }
                        }
                        isLogged.value = LaunchState.LoggedIn
                    }
                }
            },
    ) {

        val (label, loginBtn, regBtn) = createRefs()

        Image(
            modifier = modifier
                .graphicsLayer(
                    scaleY = zoomLoggedInTransition,
                    scaleX = zoomLoggedInTransition,
                    rotationZ = loggedInLabelRotate
                )
                .absoluteOffset(y = labelYPosition.dp, x = labelXPosition.dp)
                .fillMaxSize(fraction = labelSize)
                .constrainAs(label) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null
        )

        DefaultButton(
            text = NavScreen.Login.route,
            onClick = { },
            modifier = modifier
                .padding(bottom = 4.dp)
                .absoluteOffset(x = loginBtnPos.dp)
                .constrainAs(loginBtn) {
                    bottom.linkTo(regBtn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        DefaultButton(
            text = NavScreen.Registration.route,
            onClick = { },
            modifier = modifier
                .padding(bottom = 136.dp)
                .absoluteOffset(x = regBtnPos.dp)
                .constrainAs(regBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}
