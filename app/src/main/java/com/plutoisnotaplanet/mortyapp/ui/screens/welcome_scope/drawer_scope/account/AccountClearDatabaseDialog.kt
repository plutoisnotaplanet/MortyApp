package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope.drawer_scope.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.components.AnimatedButton
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultTitle

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun ClearDataBaseDialog(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean> = remember { mutableStateOf(false) },
    onClearClick: () -> Unit = {}
) {

    if (state.value) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = { state.value = false }) {
            DialogContent(
                onDismissDialogClick = { state.value = false },
                onClearClick = onClearClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogContent(
    onDismissDialogClick: () -> Unit = {},
    onClearClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(0.9f),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {

            DefaultTitle(
                value = stringResource(R.string.tv_clear_database_description),
                fontSize = 20,
                textColor = Color.Black,
                maxLines = 2,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 36.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AnimatedButton(
                    modifier = Modifier.weight(1f),
                    text = "Clear",
                    textColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = {
                        onClearClick()
                        onDismissDialogClick()
                    }
                )
                AnimatedButton(
                    modifier = Modifier.weight(1f),
                    text = "Cancel",
                    textColor = Color.Black,
                    backgroundColor = Color.White,
                    onClick = onDismissDialogClick
                )
            }
        }
    }
}

