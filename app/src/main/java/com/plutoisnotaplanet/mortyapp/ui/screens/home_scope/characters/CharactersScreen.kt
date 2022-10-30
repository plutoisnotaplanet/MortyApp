package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.extensions.paging
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultChip
import com.plutoisnotaplanet.mortyapp.application.utils.compose.StaggeredGrid
import com.plutoisnotaplanet.mortyapp.ui.main.MainEvent
import com.plutoisnotaplanet.mortyapp.ui.screens.MenuTopBarWithSearch
import com.plutoisnotaplanet.mortyapp.ui.components.SearchDisplay
import com.plutoisnotaplanet.mortyapp.ui.components.TopBarSearchState
import com.plutoisnotaplanet.mortyapp.ui.components.collectAsCompose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    viewModel: CharactersViewModel,
    lazyListState: LazyListState,
    onMainEvent: (MainEvent) -> Unit,
    selectCharacter: (Long) -> Unit,
) {

    val filterBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val searchState by viewModel.searchState

    val state =
        searchState.collectAsCompose(
            debounce = 600,
        ) { query: TextFieldValue ->
            Timber.e("query: ${query.text}")
            viewModel.searchByText(query.text)
        }

    val coroutinesScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val dispatcher: OnBackPressedDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            CharactersFloatingActionButtons(
                lazyListState = lazyListState,
                filterBottomSheetState = filterBottomSheetState,
                showFiltersDialog = { showOrHideDialog(coroutinesScope, filterBottomSheetState) },
                scrollToTop = { coroutinesScope.launch { lazyListState.animateScrollToItem(0) } }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->

        val characters by viewModel.characters
        val networkState by viewModel.networkState
        val filtersModel by viewModel.filtersState.collectAsState()

        BackHandler {
            when {
                state.focused -> {
                    state.query = TextFieldValue("")
                    state.focused = false
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
                filterBottomSheetState.isVisible -> {
                    showOrHideDialog(coroutinesScope, filterBottomSheetState)
                }
                state.topBarState == TopBarSearchState.SHOWING -> {
                    state.topBarState = TopBarSearchState.HIDDEN
                    state.query = TextFieldValue("")
                    viewModel.clearFilters()
                }
                else -> dispatcher.onBackPressed()
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = lazyListState
        ) {

            item {
                MenuTopBarWithSearch(
                    query = state.query,
                    topBarState = state.topBarState,
                    onTopBarChangeState = { state.topBarState = it },
                    onQueryChange = { state.query = it },
                    onSearchFocusChange = { state.focused = it },
                    onClearQuery = {
                        state.query = TextFieldValue("")
                        viewModel.updateData()
                    },
                    onBack = {
                        viewModel.clearFilters()
                        state.query = TextFieldValue("")
                    },
                    focused = state.focused,
                    modifier = modifier,
                    onOpenDrawerMenu = { onMainEvent(MainEvent.OpenDrawerMenu) }
                )
            }


            when (state.searchDisplay) {

                SearchDisplay.Suggestions -> {
                    item {
                        SuggestionGridLayout(
                            suggestions = state.suggestions,
                            onSuggestionClick = {
                                viewModel.prepareSuggestionsClick(it)
                                state.focused = false
                                focusManager.clearFocus()
                                state.query = TextFieldValue("")
                            }
                        )
                    }
                }

                SearchDisplay.Results -> {
                    paging(
                        items = characters,
                        filtersModel = filtersModel,
                        removeFilter = viewModel::removeFilter,
                        currentIndexFlow = viewModel.characterPageStateFlow,
                        networkState = networkState,
                        fetch = viewModel::fetchNextCharactersPage
                    ) { pagingItem ->

                        CharacterHolder(
                            character = pagingItem,
                            selectCharacter = selectCharacter,
                            onHeartClick = viewModel::addOrRemoveFromFavorites
                        )
                    }
                }
            }
        }

        ModalBottomSheetLayout(
            sheetState = filterBottomSheetState,
            sheetElevation = 24.dp,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetContent = { CharactersFilterScreen(viewModel = viewModel) },
        ) {}
    }
}

@Composable
private fun SuggestionGridLayout(
    modifier: Modifier = Modifier,
    suggestions: List<CharacterStat>,
    onSuggestionClick: (CharacterStat) -> Unit
) {
    StaggeredGrid(
        modifier = modifier.padding(8.dp)
    ) {
        suggestions.forEach { suggestionModel ->
            DefaultChip(
                modifier = Modifier.padding(4.dp),
                onClick = { onSuggestionClick(suggestionModel) },
                value = suggestionModel.viewValue
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
private fun showOrHideDialog(
    coroutineScope: CoroutineScope,
    filterBottomSheetState: ModalBottomSheetState
) {
    if (filterBottomSheetState.isVisible) {
        coroutineScope.launch { filterBottomSheetState.hide() }
    } else coroutineScope.launch {
        filterBottomSheetState.show()
    }
}

