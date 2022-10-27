package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.locations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.extensions.paging

@Composable
fun LocationsScreen(
    viewModel: LocationsViewModel,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

    val locations by viewModel.locations
    val networkState by viewModel.networkState

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            state = lazyListState,
            modifier = modifier
                .padding(paddingValues),
        ) {
            paging(
                items = locations,
                networkState = networkState,
                currentIndexFlow = viewModel.locationPageStateFlow,
                fetch = viewModel::fetchNextLocationsPage
            ) { item ->
                LocationHolder(location = item)
            }
        }
    }
}

@Composable
fun LocationHolder(
    modifier: Modifier = Modifier,
    location: Location = Location()
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Color.White
    ) {

        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                .height(156.dp)
                .fillMaxWidth(),
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.background
        ) {

            Row {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = location.name ?: stringResource(id = R.string.tv_unknown),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}