package com.plutoisnotaplanet.mortyapp.application.extensions

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharactersFilterModel
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.ActiveFilters
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

inline fun <T> LazyGridScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    threshold: Int = 4,
    pageSize: Int = 20,
    crossinline fetch: () -> Unit,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    val currentIndex = currentIndexFlow.value

    itemsIndexed(items) { index, item ->

        Timber.e("index $index currentIndex $currentIndex")

        itemContent(item)

        if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
            fetch()
        }
    }
}

inline fun <T> LazyListScope.paging(
    items: List<T>,
    currentIndexFlow: StateFlow<Int>,
    threshold: Int = 4,
    pageSize: Int = 20,
    filtersModel: CharactersFilterModel,
    crossinline fetch: () -> Unit,
    crossinline removeFilter: (CharacterStat) -> Unit,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) {
    val currentIndex = currentIndexFlow.value

    if (filtersModel.isFiltersActive) {
        item {
            ActiveFilters(filters = filtersModel) {
                removeFilter(it)
            }
        }
    }
    Timber.e("itemsSize ${items.size}")

    itemsIndexed(items) { index, item ->
        itemContent(item)

        Timber.e("${index + threshold + 1} >= ${pageSize * (currentIndex - 1)}")
        if ((index + threshold + 1) >= pageSize * (currentIndex - 1)) {
            fetch()
        }
    }
}