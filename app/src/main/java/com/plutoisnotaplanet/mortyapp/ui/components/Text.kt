package com.plutoisnotaplanet.mortyapp.application.utils.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.ui.theme.light_secondary

@Preview(showBackground = true)
@Composable
fun SubTitle14(
    modifier: Modifier = Modifier.padding(start = 12.dp),
    title: String = stringResource(id = R.string.tv_unknown),
) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        fontSize = 14.sp,
        color = Color.Black,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview
@Composable
fun DefaultTitle(
    modifier: Modifier = Modifier,
    textColor: Color = light_secondary,
    fontSize: Int = 16,
    maxLines: Int = 1,
    value: String = stringResource(id = R.string.tv_unknown),
) {
    Text(
        text = value,
        style = MaterialTheme.typography.h6,
        fontSize = fontSize.sp,
        color = textColor,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Title24(
    modifier: Modifier = Modifier.padding(start = 12.dp),
    title: String = stringResource(id = R.string.tv_unknown)
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        fontSize = 24.sp,
        color = Color.Black,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview
@Composable
fun GrayText(
    modifier: Modifier = Modifier,
    value: String = stringResource(id = R.string.tv_unknown)
) {
    Text(
        text = value,
        style = MaterialTheme.typography.h6,
        fontSize = 14.sp,
        color = Color.LightGray,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultClickableText(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.tv_unknown),
    onClick: (Int) -> Unit = {},
    ) {
    ClickableText(
        modifier = modifier,
        onClick = onClick,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = colorResource(id = R.color.colorAccent))) {
                append(title)
            }
        }
    )
}