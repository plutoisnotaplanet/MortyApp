package com.plutoisnotaplanet.mortyapp.ui.login_scope

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()

    val color by infiniteTransition.animateColor(
        initialValue = colorResource(id = R.color.white),
        targetValue = colorResource(id = R.color.colorAccent),
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = modifier
            .background(color = color)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clickable(enabled = true) {
                onClick()
            },
    ) {
        Image(
            modifier = modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = null,
        )
    }
}