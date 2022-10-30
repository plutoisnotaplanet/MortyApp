package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharactersFloatingActionButtons(
    lazyListState: LazyListState,
    filterBottomSheetState: ModalBottomSheetState,
    showFiltersDialog: () -> Unit,
    scrollToTop: () -> Unit
) {

    val isScrollUpVisible by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    val isBtnVisible by remember {
        derivedStateOf {
            filterBottomSheetState.currentValue == ModalBottomSheetValue.Hidden
        }
    }

    AnimatedVisibility(
        visible = isBtnVisible || isScrollUpVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            backgroundColor = colorResource(id = R.color.colorAccent),
            onClick = {
                if (isScrollUpVisible) {
                    scrollToTop()
                } else {
                    showFiltersDialog()
                }
            }
        ) {
            Row(
                modifier = Modifier.padding(start = if (isScrollUpVisible) 12.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isScrollUpVisible) Icons.Filled.ArrowUpward else Icons.Filled.FilterList,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                AnimatedVisibility(visible = isScrollUpVisible) {
                    Text(
                        text = "Scroll up",
                        color = Color.White,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                    )
                }
            }
        }
    }
}



