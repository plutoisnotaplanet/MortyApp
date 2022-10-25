package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.ExtendedScrollingUpButton

@Immutable
enum class FloatingButtonState {
    ScrollUp, Filters, None
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun FiltersActionButton(
    showFiltersDialog: () -> Unit,
    filterBottomSheetState: ModalBottomSheetState,
    isScrollUpVisible: Boolean,
    scrollToTop: () -> Unit
) {

    val state = remember {
        mutableStateOf(FloatingButtonState.Filters)
    }

    val isFiltersBtnVisible = remember { mutableStateOf(true) }

    isFiltersBtnVisible.value = !filterBottomSheetState.isVisible

    state.value = when {
        isScrollUpVisible -> FloatingButtonState.ScrollUp
        !filterBottomSheetState.isVisible || filterBottomSheetState.isAnimationRunning -> FloatingButtonState.Filters
        else -> FloatingButtonState.None
    }

    AnimatedContent(
        targetState = state.value,
        contentAlignment = Alignment.Center,
        transitionSpec = {
            fadeIn(animationSpec = tween(120)) +
                    scaleIn(initialScale = 0.3f, animationSpec = tween(120)) with
                    fadeOut(animationSpec = tween(90))
        }) { floatingButtonState ->

        when (floatingButtonState) {
            FloatingButtonState.ScrollUp -> {
                ExtendedScrollingUpButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    action = scrollToTop
                )
            }
            FloatingButtonState.Filters -> {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    backgroundColor = colorResource(id = R.color.colorAccent),
                    onClick = {
                        showFiltersDialog()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            FloatingButtonState.None -> {}
        }
    }
}
//        if (isFiltersBtnVisible) {
//            FloatingActionButton(
//                modifier = Modifier.padding(16.dp),
//                backgroundColor = colorResource(id = R.color.colorAccent),
//                onClick = showFiltersDialog
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.FilterList,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        }
//    }
//
//    if (isScrollUpVisible) {
//        AnimatedVisibility(
//            visible = true,
//            enter = slideInVertically(),
//            exit = slideOutVertically()
//        ) {
//            ExtendedScrollingUpButton {
//                scrollToTop()
//            }
//        }
//    } else {
//        AnimatedVisibility(
//            visible = isFiltersBtnVisible.value,
//            enter = fadeIn(),
//            exit = fadeOut()
//        ) {
//            FloatingActionButton(
//                onClick = {
//                    isFiltersBtnVisible.value = false
//                    showFiltersDialog()
//                },
//                backgroundColor = colorResource(id = R.color.colorAccent),
//                contentColor = Color.White,
//                modifier = Modifier
//                    .padding(12.dp)
//                    .size(56.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.FilterList,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        }
//    }


