package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.foundation.lazy.LazyListState

data class HomeTabStateHolder(
    val charactersLazyListState: LazyListState,
    val locationsLazyListState: LazyListState,
    val episodesLazyListState: LazyListState,
)