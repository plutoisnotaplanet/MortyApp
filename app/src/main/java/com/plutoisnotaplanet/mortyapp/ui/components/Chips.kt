package com.plutoisnotaplanet.mortyapp.application.utils.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.ui.theme.light_primary

//@Composable
//fun CancelableChip(
//    modifier: Modifier = Modifier.padding(4.dp),
//    suggestion: CharacterStat,
//    @DrawableRes drawableRes: Int = -1,
//    onClick: ((CharacterStat) -> Unit)? = null,
//    onCancel: ((CharacterStat) -> Unit)? = null
//) {
//
//    Surface(
//        elevation = 0.dp,
//        modifier = modifier,
//        color = Color(0xFFE0E0E0),
//        shape = RoundedCornerShape(16.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .clickable {
//                    onClick?.run {
//                        invoke(suggestion)
//                    }
//                }
//                .padding(vertical = 8.dp, horizontal = 10.dp)
//        ) {
//
//            if (drawableRes != -1) {
//                Image(
//                    painter = painterResource(drawableRes),
//                    modifier = Modifier
//                        .padding(end = 8.dp)
//                        .size(20.dp)
//                        .clip(CircleShape),
//                    contentDescription = null
//                )
//            }
//
//            Text(
//                text = suggestion.viewValue,
//                style = MaterialTheme.typography.body2,
//                modifier = Modifier.padding(end = 8.dp)
//            )
//
//            Surface(color = Color.DarkGray, modifier = Modifier, shape = CircleShape) {
//                IconButton(
//                    onClick = {
//                        onCancel?.run {
//                            invoke(suggestion)
//                        }
//                    },
//                    modifier = Modifier
//                        .size(16.dp)
//                        .padding(1.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Close,
//                        tint = Color(0xFFE0E0E0),
//                        contentDescription = null
//                    )
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CancellableChip(
    modifier: Modifier = Modifier.padding(4.dp),
    value: String = stringResource(id = R.string.tv_unknown),
    onClick: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    FilterChip(
        modifier = modifier,
        selected = false,
        onClick = onClick,
        trailingIcon = {
            IconButton(
                onClick = onCancel,
                modifier = Modifier
                    .size(20.dp)
                    .padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        }
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Preview
@Composable
fun DefaultChip(
    modifier: Modifier = Modifier.padding(4.dp),
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        .compositeOver(MaterialTheme.colors.surface),
    value: String = stringResource(id = R.string.tv_unknown),
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 15)),
        color = backgroundColor
    ) {
        Row(modifier = Modifier
            .clickable(onClick = onClick)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun SelectableChip(
    modifier: Modifier = Modifier.padding(4.dp),
    value: String = stringResource(id = R.string.tv_unknown),
    isSelected: Boolean = false,
    onSelected: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 15)),
        color = if (isSelected) light_primary else Color.Gray
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = { onSelected() }
            )
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FiltersChipGroup(
    chips: List<CharacterStat> = emptyList(),
    selectedChip: CharacterStat? = null,
    onSelectedChanged: (CharacterStat) -> Unit = {},
) {
    if (chips.isEmpty()) return
    Column(modifier = Modifier.padding(8.dp)) {
        StaggeredGrid {
            chips.forEach { chip ->
                SelectableChip(
                    value = chip.viewValue,
                    isSelected = chip == selectedChip,
                    onSelected = { onSelectedChanged(chip) }
                )
            }
        }
    }
}