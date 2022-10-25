package com.plutoisnotaplanet.mortyapp.application.utils.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus

@Composable
fun CancelableChip(
    modifier: Modifier = Modifier.padding(4.dp),
    suggestion: CharacterStat,
    @DrawableRes drawableRes: Int = -1,
    onClick: ((CharacterStat) -> Unit)? = null,
    onCancel: ((CharacterStat) -> Unit)? = null
) {

    Surface(
        elevation = 0.dp,
        modifier = modifier,
        color = Color(0xFFE0E0E0),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick?.run {
                        invoke(suggestion)
                    }
                }
                .padding(vertical = 8.dp, horizontal = 10.dp)
        ) {

            if (drawableRes != -1) {
                Image(
                    painter = painterResource(drawableRes),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp)
                        .clip(CircleShape),
                    contentDescription = null
                )
            }

            Text(
                text = suggestion.viewValue,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(end = 8.dp)
            )

            Surface(color = Color.DarkGray, modifier = Modifier, shape = CircleShape) {
                IconButton(
                    onClick = {
                        onCancel?.run {
                            invoke(suggestion)
                        }
                    },
                    modifier = Modifier
                        .size(16.dp)
                        .padding(1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        tint = Color(0xFFE0E0E0),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Chip(
    filter: CharacterStat = CharacterStatus.Unknown,
    isSelected: Boolean = false,
    onSelectionChanged: (CharacterStat) -> Unit = {},
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) colorResource(id = R.color.colorPrimary) else colorResource(id = R.color.colorAccent)
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged(filter)
                }
            )
        ) {
            Text(
                text = filter.viewValue,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroup(
    chips: List<CharacterStat> = emptyList(),
    selectedChip: CharacterStat? = null,
    onSelectedChanged: (CharacterStat) -> Unit = {},
) {
    if (chips.isEmpty()) return
    Column(modifier = Modifier.padding(8.dp)) {
        StaggeredGrid {
            chips.forEach { chip ->
                Chip(
                    filter = chip,
                    isSelected = chip == selectedChip,
                    onSelectionChanged = onSelectedChanged
                )
            }
        }
    }
}