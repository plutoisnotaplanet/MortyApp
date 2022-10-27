package com.plutoisnotaplanet.mortyapp.application.extensions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.utils.compose.Title24
import com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters.ActiveFilters
import kotlinx.coroutines.flow.StateFlow

inline fun <T> LazyGridScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    networkState: Response<Any>,
    threshold: Int = 4,
    pageSize: Int = 20,
    crossinline fetch: () -> Unit,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    val currentIndex = currentIndexFlow.value

    itemsIndexed(items) { index, item ->

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
            fetch()
        }
    }

    when (networkState) {
        is Response.Loading -> {
            item {
                if (currentIndex == 1) {
                    ShowLoader(
                        modifier = Modifier
                    )
                } else {
                    ShowLoader(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        is Response.Error -> {
            item {
                if (currentIndex == 1) {
                    ShowError(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        is Response.Success -> {}
    }
}

inline fun <T> LazyListScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    networkState: Response<Any>,
    threshold: Int = 4,
    pageSize: Int = 20,
    filtersModel: CharactersFilterModel = CharactersFilterModel(),
    crossinline fetch: () -> Unit,
    crossinline removeFilter: (CharacterStat) -> Unit = {},
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val currentIndex = currentIndexFlow.value.takeIf { it != 1 } ?: 2

    if (filtersModel.isFiltersActive) {
        item {
            ActiveFilters(filters = filtersModel) {
                removeFilter(it)
            }
        }
    }

    itemsIndexed(items) { index, item ->

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
            fetch()
        }
    }

        when (networkState) {
            is Response.Loading -> {
                item {
                    if (currentIndex == 2) {
                        ShowLoader(
                            modifier = Modifier.fillParentMaxSize()
                        )
                    } else {
                        ShowLoader(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            is Response.Error -> {
                item {
                    if (currentIndex == 1) {
                        ShowError(
                            modifier = Modifier.fillParentMaxSize()
                        )
                    }
                }
            }
            is Response.Success -> {}
    }
}

@Composable
fun ShowLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
fun ShowError(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Title24(
            modifier = Modifier
                .align(Alignment.Center),
            title = stringResource(id = R.string.tt_result_not_found)
        )
    }
}