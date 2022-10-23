package com.plutoisnotaplanet.mortyapp.application.data.rest.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.plutoisnotaplanet.mortyapp.ui.theme.shimmerHighLight
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shimmerParams: ShimmerParams = ShimmerParams(
        baseColor = MaterialTheme.colors.background,
        highlightColor = shimmerHighLight,
        dropOff = 0.65f
    )
) {
    if (imageUrl == null) return
    CoilImage(
        imageModel = imageUrl,
        modifier = modifier,
        contentScale = contentScale,
        shimmerParams = shimmerParams,
        failure = {
            Text(
                text = "image request failed.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}