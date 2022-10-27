package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.utils.compose.FiltersChipGroup
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultClickableText
import com.plutoisnotaplanet.mortyapp.application.utils.compose.SubTitle14
import com.plutoisnotaplanet.mortyapp.application.utils.compose.Title24

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

            DefaultClickableText(
                modifier = Modifier
                    .constrainAs(clearFilters) {
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    viewModel.clearFilters()
                },
                title = stringResource(id = R.string.tt_clear_filters)
            )
        }

    }

    Spacer(modifier = Modifier.height(16.dp))

    SubTitle14(
        title = stringResource(id = R.string.tt_filter_by_status)
    )

    FiltersChipGroup(
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

    FiltersChipGroup(
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

    FiltersChipGroup(
        chips = speciesList,
        selectedChip = currentFilter.species,
        onSelectedChanged = { specie ->
            viewModel.addFilter(
                newFilter = specie
            )
        }
    )
}
