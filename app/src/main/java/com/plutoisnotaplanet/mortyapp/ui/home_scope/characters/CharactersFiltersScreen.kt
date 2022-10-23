package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterGender
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterSpecies
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStat
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus
import com.plutoisnotaplanet.mortyapp.application.utils.ChipGroup
import com.plutoisnotaplanet.mortyapp.application.utils.SubTitle14
import com.plutoisnotaplanet.mortyapp.application.utils.Title24

@Composable
fun CharactersFilterScreen(
    viewModel: CharactersViewModel
) {
    val statusesList = viewModel.statuses
    val genderList = viewModel.genders
    val speciesList = viewModel.species

    val currentFilter = viewModel.filtersState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(36.dp)
        ) {

            val (title, clearFilters) = createRefs()

            Title24(
                title = stringResource(id = R.string.tt_filters),
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            ClickableText(
                modifier = Modifier
                    .constrainAs(clearFilters) {
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    viewModel.clearFilters()
                },
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.colorAccent))) {
                        append(stringResource(id = R.string.tt_clear_filters))
                    }
                }
            )
        }

    }

    Spacer(modifier = Modifier.height(16.dp))

    SubTitle14(
        title = stringResource(id = R.string.tt_filter_by_status)
    )

    ChipGroup(
        chips = statusesList,
        selectedChip = currentFilter.status,
        onSelectedChanged = { status ->
            viewModel.addFilter(
                newFilter = status
            )
        }
    )

    SubTitle14(
        title = stringResource(id = R.string.tt_filter_by_gender)
    )

    ChipGroup(
        chips = genderList,
        selectedChip = currentFilter.gender,
        onSelectedChanged = { gender ->
            viewModel.addFilter(
                newFilter = gender
            )
        }
    )

    SubTitle14(
        title = stringResource(id = R.string.tt_filter_by_specie)
    )

    ChipGroup(
        chips = speciesList,
        selectedChip = currentFilter.species,
        onSelectedChanged = { specie ->
            viewModel.addFilter(
                newFilter = specie
            )
        }
    )
}
