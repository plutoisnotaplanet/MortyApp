package com.plutoisnotaplanet.mortyapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.theme.light_accent_color

@Composable
fun BorderColumn(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderStroke: BorderStroke = BorderStroke(1.dp, light_accent_color),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        border = borderStroke
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = onClick != null) { onClick?.invoke() }

        ) {
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DataField(
    modifier: Modifier = Modifier,
    name: String = stringResource(id = R.string.tv_unknown),
    value: String = stringResource(id = R.string.tv_unknown)
) {

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = name,
            style = MaterialTheme.typography.body2.copy(color = Color.Gray),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2.copy(color = Color.Black),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}