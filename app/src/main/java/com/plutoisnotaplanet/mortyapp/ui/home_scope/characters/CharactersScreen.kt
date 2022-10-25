package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
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
import com.plutoisnotaplanet.mortyapp.application.utils.compose.CancelableChip
import com.plutoisnotaplanet.mortyapp.application.utils.compose.StaggeredGrid
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.CollectAsCompose
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.ExtendedScrollingUpButton
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.SearchBar
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.SearchDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class, ExperimentalMaterialApi::class
)
@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    selectCharacter: (Long) -> Unit,
) {
    val filterBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val searchState by viewModel.searchState

    val isScrollUp by derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }

    val state =
        searchState.CollectAsCompose(
            debounce = 600,
        ) { query: TextFieldValue ->
            viewModel.searchByText(query.text)
        }

    val coroutinesScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FiltersActionButton(
                filterBottomSheetState = filterBottomSheetState,
                showFiltersDialog = { showOrHideDialog(coroutinesScope, filterBottomSheetState) },
                isScrollUpVisible = isScrollUp,
                scrollToTop = { coroutinesScope.launch { lazyListState.animateScrollToItem(0) } }
            )
        },
        floatingActionButtonPosition = if (isScrollUp) FabPosition.Center else FabPosition.End,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            val dispatcher: OnBackPressedDispatcher =
                LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

            BackHandler(enabled = filterBottomSheetState.isVisible) {
                showOrHideDialog(coroutinesScope, filterBottomSheetState)
            }

            BackHandler(
                enabled = state.focused
            ) {
                if (!state.focused) {
                    dispatcher.onBackPressed()
                } else {
                    state.query = TextFieldValue("")
                    state.focused = false
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }

            SearchBar(
                query = state.query,
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
                modifier = modifier
            )

            when (state.searchDisplay) {

                SearchDisplay.Suggestions -> {
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

                SearchDisplay.Results -> {
                    CharactersListScreen(
                        viewModel = viewModel,
                        lazyListState = lazyListState,
                        selectCharacter = selectCharacter
                    )
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
        modifier = modifier.padding(4.dp)
    ) {
        suggestions.forEach { suggestionModel ->
            CancelableChip(
                suggestion = suggestionModel,
                onClick = {
                    onSuggestionClick(it)
                }
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

