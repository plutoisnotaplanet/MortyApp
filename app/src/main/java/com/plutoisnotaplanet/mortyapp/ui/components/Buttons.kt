package com.plutoisnotaplanet.mortyapp.ui.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.components.animations.pushedAnimation
import timber.log.Timber


@Preview
@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = stringResource(id = R.string.tv_unknown),
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        enabled = enabled,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.colorAccent)
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.body2.copy(
                color = Color.White, fontWeight = FontWeight.SemiBold
            ), modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun AnimatedButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.tv_unknown),
    textColor: Color = Color.White,
    backgroundColor: Color = MaterialTheme.colors.primary,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        contentPadding = PaddingValues(12.dp),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .pushedAnimation(onClick = onClick)
    ) {
        Timber.e("animated button compose $text")
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            fontSize = 18.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}