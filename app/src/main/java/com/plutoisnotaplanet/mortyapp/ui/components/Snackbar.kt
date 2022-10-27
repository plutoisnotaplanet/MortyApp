package com.plutoisnotaplanet.mortyapp.application.utils.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorSnackBar(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    errorMessage: String,
    onDismiss: () -> Unit = { }
) {
//    AnimatedVisibility(
//        visible = true,
//        enter = slideInVertically(initialOffsetY = { it }),
//        exit = slideOutVertically(targetOffsetY = { it }),
//        modifier = modifier
//    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = "Ok",
                        )
                    }
                }
            ) {
                Text(errorMessage)
            }
        })
//    }
}