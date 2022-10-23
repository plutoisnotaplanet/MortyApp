package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

data class HomeTabStateHolder(
    val charactersLazyListState: LazyListState,
    val locationsLazyGridState: LazyListState,
    val episodesLazyListState: LazyListState,
)