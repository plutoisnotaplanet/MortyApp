package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersFloatingActionButtons(
    showFiltersDialog: () -> Unit,
    filterBottomSheetState: ModalBottomSheetState,
    isScrollUpVisible: Boolean,
    scrollToTop: () -> Unit
) {

    val state = remember {
        mutableStateOf(FloatingButtonState.Filters)
    }

    val isFiltersBtnVisible by derivedStateOf {
        mutableStateOf(!filterBottomSheetState.isVisible)
    }

    state.value = when {
        isFiltersBtnVisible.value && !isScrollUpVisible -> FloatingButtonState.Filters
        isScrollUpVisible -> FloatingButtonState.ScrollUp
        else -> FloatingButtonState.None
    }

    when (state.value) {
        FloatingButtonState.Filters -> {
            FloatingActionButton(
                backgroundColor = colorResource(id = R.color.colorAccent),
                onClick = {
                    isFiltersBtnVisible.value = false
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
        FloatingButtonState.ScrollUp -> {
            ExtendedScrollingUpButton(
                action = scrollToTop
            )
        }
        FloatingButtonState.None -> {}
    }

    //TODO: fix animation bug: Filters button is not visible after closing bottom sheet
//
//    AnimatedContent(
//        targetState = state.value,
//        transitionSpec = {
//            fadeIn(animationSpec = tween(120)) +
//                    scaleIn(initialScale = 0.3f, animationSpec = tween(120)) with
//                    fadeOut(animationSpec = tween(90))
//        }) { floatingButtonState ->
//
//
//    }
}



