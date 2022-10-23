package com.plutoisnotaplanet.mortyapp.application.utils

import android.graphics.Point
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

/**
 * Staggered grid layout for displaying items as GridLayout in classic View
 */
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables: List<Measurable>, constraints: Constraints ->

        val constraintMaxWidth = constraints.maxWidth
        val constraintMaxHeight = constraints.maxHeight

        var maxRowWidth = 0

        var currentWidthOfRow = 0
        var totalHeightOfRows = 0

        var xPos: Int
        var yPos: Int

        val placeableMap = linkedMapOf<Int, Point>()
        val rowHeights = mutableListOf<Int>()

        var maxPlaceableHeight = 0
        var lastRowHeight = 0

//        println("😈 MyStaggeredGrid() constraintMaxWidth: $constraintMaxWidth, constraintMaxHeight: $constraintMaxHeight")

        val placeables: List<Placeable> = measurables.mapIndexed { index, measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)
            val placeableWidth = placeable.width
            val placeableHeight = placeable.height


            val isSameRow = (currentWidthOfRow + placeableWidth <= constraintMaxWidth)

            if (isSameRow) {

                xPos = currentWidthOfRow
                yPos = totalHeightOfRows

                // Current width or row is now existing length and new item's length
                currentWidthOfRow += placeableWidth

                // Get the maximum item height in each row
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                // After adding each item check if it's the longest row
                maxRowWidth = maxRowWidth.coerceAtLeast(currentWidthOfRow)

                lastRowHeight = maxPlaceableHeight

//                println(
//                    "🍎 Same row->  " +
//                            "currentWidthOfRow: $currentWidthOfRow, " +
//                            "placeableHeight: $placeableHeight"
//                )

            } else {

                currentWidthOfRow = placeableWidth
                maxPlaceableHeight = maxPlaceableHeight.coerceAtLeast(placeableHeight)

                totalHeightOfRows += maxPlaceableHeight

                xPos = 0
                yPos = totalHeightOfRows

                rowHeights.add(maxPlaceableHeight)

                lastRowHeight = maxPlaceableHeight
                maxPlaceableHeight = placeableHeight

//                println(
//                    "🍏 New column-> " +
//                            "currentWidthOfRow: $currentWidthOfRow, " +
//                            "totalHeightOfRows: $totalHeightOfRows, " +
//                            "placeableHeight: $placeableHeight"
//                )
            }

            placeableMap[index] = Point(xPos, yPos)
            placeable
        }


        val finalHeight = (rowHeights.sumOf { it } + lastRowHeight)
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))


//        println("RowHeights: $rowHeights, finalHeight: $finalHeight")

        // Set the size of the layout as big as it can
        layout(maxRowWidth, finalHeight) {
            // Place children in the parent layout
            placeables.forEachIndexed { index, placeable ->
                // Position item on the screen

                val point = placeableMap[index]
                point?.let {
                    placeable.placeRelative(x = point.x, y = point.y)
                }
            }
        }
    }
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
