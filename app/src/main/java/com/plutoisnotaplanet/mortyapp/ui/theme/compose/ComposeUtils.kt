package com.plutoisnotaplanet.mortyapp.application.utils.compose

import android.graphics.Point
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus


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

