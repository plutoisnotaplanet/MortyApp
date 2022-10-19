package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.data.rest.compose.CharacterImage
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.extensions.paging
import com.plutoisnotaplanet.mortyapp.application.utils.CancelableChip
import com.plutoisnotaplanet.mortyapp.application.utils.StaggeredGrid
import com.plutoisnotaplanet.mortyapp.application.utils.Title24

@Composable
fun CharactersListScreen(
    viewModel: CharactersViewModel,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    selectCharacter: (Long) -> Unit,
) {
    val networkResponse: NetworkResponse<BaseResponse<Character>> by viewModel.characters

    val filtersModel by viewModel.filtersState.collectAsState()

    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        networkResponse.OnSuccess { characters ->
            LazyColumn(
                state = lazyListState,
                modifier = modifier
                    .padding(innerPadding)
                    .background(Color.White)
            ) {

                paging(
                    items = characters.results,
                    filtersModel = filtersModel,
                    removeFilter = { viewModel.removeFilter(it) },
                    currentIndexFlow = viewModel.characterPageStateFlow,
                    fetch = { viewModel.fetchNextCharactersPage() }
                ) { item ->

                    CharacterHolder(
                        character = item,
                        selectCharacter = selectCharacter
                    )
                }
            }
        }

        when(networkResponse) {
            NetworkResponse.Loading -> ShowLoader()
            is NetworkResponse.Error -> ShowError()
            else -> {}
        }
    }
}

@Composable
fun ShowLoader() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
fun ShowError() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Title24(
            modifier = Modifier.align(Alignment.Center),
            title = stringResource(id = R.string.tt_result_not_found)
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterHolder(
    modifier: Modifier = Modifier,
    character: Character = Character(),
    selectCharacter: (Long) -> Unit,
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
            backgroundColor = MaterialTheme.colors.background,
            onClick = { selectCharacter(character.id) }
        ) {

            Row {
                CharacterImage(
                    imageUrl = character.image,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.4f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Text(
                        text = character.name ?: stringResource(id = R.string.tv_unknown),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    CharacterStatus(character = character)

                    Spacer(modifier = Modifier.height(8.dp))

                    CharacterInfoField(
                        title = stringResource(id = R.string.tt_last_known_location),
                        value = character.location?.name ?: stringResource(id = R.string.tv_unknown)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    CharacterInfoField(
                        title = stringResource(id = R.string.tt_first_seen_in),
                        value = character.origin?.name ?: stringResource(id = R.string.tv_unknown)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterStatus(
    character: Character
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Canvas(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(4.dp)
        ) {
            drawCircle(
                color = character.status.color,
                radius = 4.dp.toPx()
            )
        }

        Text(
            text = character.status.paramName,
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )

        Text(
            text = " - ${character.species}",
            style = MaterialTheme.typography.body2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun CharacterInfoField(
    title: String,
    value: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun ActiveFilters(
    filters: CharactersFilterModel,
    removeFilter: (CharacterStat) -> Unit,
) {
    StaggeredGrid(modifier = Modifier.padding(8.dp)) {
        filters.status?.let {
            CancelableChip(
                suggestion = it,
                onCancel = {
                    removeFilter(it)
                }
            )
        }
        filters.gender?.let {
            CancelableChip(
                suggestion = it,
                onCancel = {
                    removeFilter(it)
                }
            )
        }
        filters.species?.let {
            CancelableChip(
                suggestion = it,
                onCancel = {
                    removeFilter(it)
                }
            )
        }
    }
}

@Preview
@Composable
fun previewCharacterHolder() {
    CharacterHolder(selectCharacter = {})
}