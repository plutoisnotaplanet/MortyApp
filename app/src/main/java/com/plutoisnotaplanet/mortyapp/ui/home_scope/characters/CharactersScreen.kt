package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.plutoisnotaplanet.mortyapp.ui.home_scope.episodes.EpisodesViewModel
import com.plutoisnotaplanet.mortyapp.ui.main.MainScreenHomeTab

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel,
    selectPoster: (MainScreenHomeTab, Long) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

}