package com.plutoisnotaplanet.mortyapp.ui.theme.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.ui.theme.shimmerHighLight
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Preview(showBackground = true)
@Composable
fun AvatarImage(
    modifier: Modifier = Modifier,
    avatar: Any = Icons.Filled.AccountCircle,
    onClick: () -> Unit = {},
) {
    CoilImage(
        imageModel = avatar,
        modifier = modifier
            .size(size = 120.dp)
            .clip(shape = CircleShape)
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(),
        failure = {
            Icon(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultImage(
    modifier: Modifier = Modifier,
    photo: Any = Icons.Filled.AccountCircle,
    onClick: () -> Unit = {},
) {
    CoilImage(
        imageModel = photo,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colors.background,
            highlightColor = shimmerHighLight,
            dropOff = 0.65f
        ),
        failure = {
            Icon(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }
    )
}