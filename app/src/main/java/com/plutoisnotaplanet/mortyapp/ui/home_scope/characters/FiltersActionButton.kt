package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.utils.isScrollingUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun FiltersActionButton(
    coroutineScope: CoroutineScope,
    lazyListState: LazyListState,
    showFiltersDialog: () -> Unit,
    filterBottomSheetState: ModalBottomSheetState
) {
    Timber.e("isDialogVisible ${filterBottomSheetState.isVisible}")
    val isScrollingUp = lazyListState.isScrollingUp()
    val isVisible = remember { mutableStateOf(true) }

    val icon = if (!isScrollingUp) Icons.Filled.KeyboardArrowUp else Icons.Filled.Search

    isVisible.value = if (filterBottomSheetState.targetValue.name == "Hidden") {
        true
    } else isVisible.value

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            onClick = {
                isVisible.value = false
                if (!isScrollingUp) {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                } else {
                    showFiltersDialog()
                }
            },
            backgroundColor = colorResource(id = R.color.colorPrimary),
            contentColor = Color.White,
            modifier = Modifier
                .padding(12.dp)
                .size(56.dp)
        ) {
            AnimatedContent(targetState = icon) {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }

}