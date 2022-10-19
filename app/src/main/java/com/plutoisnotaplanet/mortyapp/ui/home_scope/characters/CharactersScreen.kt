package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharactersFilterModel
import com.plutoisnotaplanet.mortyapp.application.utils.CancelableChip
import com.plutoisnotaplanet.mortyapp.application.utils.StaggeredGrid
import com.plutoisnotaplanet.mortyapp.ui.common.SearchBar
import com.plutoisnotaplanet.mortyapp.ui.common.SearchDisplay
import com.plutoisnotaplanet.mortyapp.ui.common.rememberSearchState
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
        ModalBottomSheetValue.Hidden
    )

    val coroutinesScope = rememberCoroutineScope()

    Scaffold (
        modifier = modifier
            .background(Color.White)
            .fillMaxSize(),
        floatingActionButton = {
            FiltersActionButton(
                isVisible = !filterBottomSheetState.isVisible) {
                showOrHideDialog(coroutinesScope, filterBottomSheetState)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val state =
                rememberSearchState(
                    suggestions = viewModel.suggestionsList,
                    timeoutMillis = 600,
                ) { query: TextFieldValue ->
                    viewModel.searchByText(query.text)
                }

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            val dispatcher: OnBackPressedDispatcher =
                LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher

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
                showOrHideDialog(coroutinesScope, filterBottomSheetState)
            }

            SearchBar(
                query = state.query,
                onQueryChange = { state.query = it },
                onSearchFocusChange = { state.focused = it },
                onClearQuery = { state.query = TextFieldValue("") },
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
                        suggestions = state.suggestions as List<CharacterStat>,
                        onSuggestionClick = { filter ->
                            var text = state.query.text
                            if (text.isEmpty()) text = filter.paramName else text += " ${filter.paramName}"
                            text.trim()
                            // Set text and cursor position to end of text
                            state.query = TextFieldValue(text, TextRange(text.length))
                            viewModel.resetCharacters()
                        }, onCancelClick = {
                        })
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
            sheetContent = { CharactersFilterScreen(viewModel = viewModel) }
        ) {}
    }
}

@Composable
private fun SuggestionGridLayout(
    modifier: Modifier = Modifier,
    suggestions: List<CharacterStat>,
    onSuggestionClick: (CharacterStat) -> Unit,
    onCancelClick: (CharacterStat) -> Unit
) {
    StaggeredGrid(
        modifier = modifier.padding(4.dp)
    ) {
        suggestions.forEach { suggestionModel ->
            CancelableChip(
                suggestion = suggestionModel,
                onClick = {
                    onSuggestionClick(it)
                },
                onCancel = {
                    onCancelClick(it)
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
    } else coroutineScope.launch { filterBottomSheetState.show() }
}

