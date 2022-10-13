package com.plutoisnotaplanet.mortyapp.ui.home_scope.locations

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plutoisnotaplanet.mortyapp.ui.main.MainScreenHomeTab

@Composable
fun LocationsScreen(
    viewModel: LocationsViewModel,
    selectPoster: (MainScreenHomeTab, Long) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

}