package com.plutoisnotaplanet.mortyapp.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class SearchState<S> internal constructor(
    suggestions: List<S>
) {
    /**
     * Query [TextFieldValue] that contains text and query selection position.
     */
    var query by mutableStateOf(TextFieldValue())

    /**
     * TopBarState [TopBarSearchState] menu top bar state.
     */
    var topBarState by mutableStateOf(TopBarSearchState.HIDDEN)

    /**
     * Flag  Search composable(TextField) focus state.
     */
    var focused by mutableStateOf(false)

    /**
     * Suggestions might contain keywords and display chips to show when Search Composable
     * is focused but query is empty.
     */
    var suggestions by mutableStateOf(suggestions)

    /**
     * Last query text, it might be used to prevent doing search when current query and previous
     * query texts are same.
     */
    var previousQueryText = ""

    val searchDisplay: SearchDisplay
        get() = when {
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            else -> {
                SearchDisplay.Results
            }
        }

    override fun toString(): String {
        return "🚀 STATE\n" +
                "query: ${query.text}, focused: $focused\n" +
                " searchDisplay: $searchDisplay\n\n"
    }

    /**
     * Check if user is running same query as the previous one
     */
    fun sameAsPreviousQuery() = query.text == previousQueryText
}

enum class SearchDisplay {
    Suggestions, Results
}

enum class TopBarSearchState {
    HIDDEN,
    SHOWING
}