package com.plutoisnotaplanet.mortyapp.ui.components.animations

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.semantics.Role
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.pushedAnimation(
    onClick: (() -> Unit)? = null
): Modifier = composed {

    val touched = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (touched.value) 0.95f else 1f)

    this
        .scale(scale.value)
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    touched.value = true
                }
                MotionEvent.ACTION_UP -> {
                    touched.value = false
                    onClick?.invoke()
                }
                MotionEvent.ACTION_CANCEL -> {
                    touched.value = false
                }
            }
            true
        }
}