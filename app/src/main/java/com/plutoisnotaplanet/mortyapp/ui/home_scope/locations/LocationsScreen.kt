package com.plutoisnotaplanet.mortyapp.ui.home_scope.locations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.plutoisnotaplanet.mortyapp.application.data.rest.compose.NetworkImage
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.extensions.paging
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.CharacterInfoField
import com.plutoisnotaplanet.mortyapp.ui.home_scope.characters.CharacterStatus

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

@OptIn(ExperimentalMaterialApi::class)
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
//
//                    CharacterStatus(character = character)
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    CharacterInfoField(
//                        title = stringResource(id = R.string.tt_last_known_location),
//                        value = character.location?.name ?: stringResource(id = R.string.tv_unknown)
//                    )
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    CharacterInfoField(
//                        title = stringResource(id = R.string.tt_first_seen_in),
//                        value = character.origin?.name ?: stringResource(id = R.string.tv_unknown)
//                    )
                }
            }
        }
    }
}