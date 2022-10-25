package com.plutoisnotaplanet.mortyapp.ui.theme.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.plutoisnotaplanet.mortyapp.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExtendedScrollingUpButton(
    modifier: Modifier = Modifier,
    action: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth()) {
        ExtendedFloatingActionButton(
            modifier = modifier.align(Alignment.Center),
            text = { Text(
                text = stringResource(id = R.string.tv_scroll_up),
                color = Color.White
            ) },
            backgroundColor = colorResource(id = R.color.colorAccent),
            icon = { Icon(imageVector = Icons.Filled.ArrowUpward, contentDescription = null, tint = Color.White) },
            onClick = action
        )
    }
}