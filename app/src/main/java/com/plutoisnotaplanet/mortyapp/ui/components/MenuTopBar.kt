package com.plutoisnotaplanet.mortyapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.ui.components.SearchTextField
import com.plutoisnotaplanet.mortyapp.ui.components.TopBarSearchState
import com.plutoisnotaplanet.mortyapp.ui.components.animations.pushedAnimation

@Preview(showBackground = true)
@Composable
fun MenuTopBar(
    onActionClick: () -> Unit = {}
) {
    TopBarScope {
        MenuButton{ onActionClick() }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun MenuTopBarWithSearch(
    query: TextFieldValue = TextFieldValue(""),
    topBarState: TopBarSearchState = TopBarSearchState.HIDDEN,
    onTopBarChangeState: (TopBarSearchState) -> Unit = {},
    onQueryChange: (TextFieldValue) -> Unit = {},
    onSearchFocusChange: (Boolean) -> Unit = {},
    onClearQuery: () -> Unit = {},
    onBack: () -> Unit = {},
    onOpenDrawerMenu: () -> Unit = {},
    focused: Boolean = false,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    AnimatedContent(targetState = topBarState) { state ->
        when (state) {
            TopBarSearchState.HIDDEN -> {
                TopBarScope {
                    MenuButton { onOpenDrawerMenu() }
                    SearchButton { onTopBarChangeState(TopBarSearchState.SHOWING) }
                }
            }
            TopBarSearchState.SHOWING -> {
                TopBarScope {
                    BackButton {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        if (focused) {
                            onBack()
                        }
                        onTopBarChangeState(TopBarSearchState.HIDDEN)
                    }
                    SearchTextField(
                        query,
                        onQueryChange,
                        onSearchFocusChange,
                        onClearQuery,
                        focused,
                        modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopBarScope(
    content: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.White,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchButton(
    onActionClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.padding(start = 2.dp),
        onClick = onActionClick
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun BackButton(
    onActionClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier.padding(start = 2.dp),
        onClick = onActionClick
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuButton(
    onActionClick: () -> Unit = {}
) {
    IconButton(
        onClick = onActionClick,
        modifier = Modifier.padding(start = 2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = null
        )
    }
}
