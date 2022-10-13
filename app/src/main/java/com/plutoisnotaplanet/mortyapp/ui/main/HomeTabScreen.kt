package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.CharactersScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.episodes.EpisodesScreen
import com.plutoisnotaplanet.mortyapp.ui.home_scope.locations.LocationsScreen

@Composable
fun HomeTabScreen(
    viewModel: MainViewModel,
    tabStateHolder: HomeTabStateHolder,
    selectItem: (MainScreenHomeTab, Long) -> Unit
) {
    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenHomeTab.values()

    Scaffold(
        backgroundColor = MaterialTheme.colors.primarySurface,
        bottomBar = {

            BottomNavigation(
                backgroundColor = Color.Cyan,
                modifier = Modifier
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
            ) {

                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                        label = { Text(text = stringResource(tab.title), color = Color.White) },
                        selected = tab == selectedTab,
                        onClick = { viewModel.selectTab(tab) },
                        selectedContentColor = LocalContentColor.current,
                        unselectedContentColor = LocalContentColor.current,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        Crossfade(selectedTab) { destination ->
            when (destination) {
                MainScreenHomeTab.CHARACTERS -> CharactersScreen(
                    hiltViewModel(),
                    selectItem,
                    tabStateHolder.charactersLazyListState,
                    modifier,
                )
                MainScreenHomeTab.LOCATIONS -> LocationsScreen(
                    hiltViewModel(),
                    selectItem,
                    tabStateHolder.locationsLazyListState,
                    modifier,
                )
                MainScreenHomeTab.EPISODES -> EpisodesScreen(
                    hiltViewModel(),
                    selectItem,
                    tabStateHolder.episodesLazyListState,
                    modifier,
                )
            }
        }
    }
}