package com.plutoisnotaplanet.mortyapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.plutoisnotaplanet.mortyapp.ui.theme.shimmerHighLight
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Preview(showBackground = true)
@Composable
fun NetworkImage(
    imageUrl: Any? = Icons.Default.AccountCircle,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shimmerParams: ShimmerParams = ShimmerParams(
        baseColor = MaterialTheme.colors.background,
        highlightColor = shimmerHighLight,
        dropOff = 0.65f
    )
) {
    CoilImage(
        imageModel = imageUrl,
        modifier = modifier,
        contentScale = contentScale,
        shimmerParams = shimmerParams,
        failure = {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.BrokenImage,
                contentDescription = null
            )
        }
    )
}