package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.Constants
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.utils.compose.DefaultTitle
import com.plutoisnotaplanet.mortyapp.ui.components.BorderColumn
import com.plutoisnotaplanet.mortyapp.ui.components.DataField
import com.plutoisnotaplanet.mortyapp.ui.components.DefaultImage
import com.plutoisnotaplanet.mortyapp.ui.theme.light_surface
import timber.log.Timber

@Preview
@Composable
fun AccountData(
    modifier: Modifier = Modifier,
    userProfile: UserProfile = UserProfile()
) {

    AccountEmail(modifier = Modifier.padding(top = 16.dp), email = userProfile.email)

    if (userProfile.isNotEmpty) {
        AccountDataBaseData(
            modifier = Modifier.padding(top = 16.dp),
            userProfile = userProfile
        )
    }

    if (!userProfile.favoriteCharactersList.isNullOrEmpty()) {
        AccountFavoritesCharacters(
            modifier = Modifier.padding(top = 16.dp),
            userProfile = userProfile
        )
    }
}

@Preview
@Composable
fun AccountFavoritesCharacters(
    modifier: Modifier = Modifier,
    userProfile: UserProfile = UserProfile()
) {

    BorderColumn(
        modifier = modifier,
        backgroundColor = Color.White
    ) {

        DefaultTitle(
            value = stringResource(id = R.string.tt_favorite_characters),
            modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(userProfile.favoriteCharactersList!!) { character ->
                CharacterThumb(character = character)
            }
        }
    }

}

@Composable
fun AccountEmail(
    modifier: Modifier = Modifier,
    email: String?
) {
    BorderColumn(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = Color.White
    ) {
        DefaultTitle(
            value = stringResource(R.string.tt_email_dots),
            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
        )
        Text(
            text = email ?: stringResource(id = R.string.tv_empty_email),
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun AccountDataBaseData(
    modifier: Modifier = Modifier,
    userProfile: UserProfile = UserProfile()
) {
    Timber.e("userProfile: ${userProfile.countOfLocalCharacters}")
    BorderColumn(
        modifier = modifier
            .fillMaxWidth(),
        backgroundColor = Color.White
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {

            DefaultTitle(
                value = stringResource(R.string.tt_database_data),
                modifier = Modifier.padding(start = 12.dp)
            )

            DefaultTitle(
                value = stringResource(R.string.tt_uploaded_slash_all),
                modifier = Modifier.padding(end = 12.dp)
            )
        }

        DataField(
            modifier = Modifier.padding(top = 8.dp),
            name = stringResource(id = R.string.menu_characters),
            value = stringResource(
                id = R.string.tv_data_with_slash,
                userProfile.countOfLocalCharacters,
                Constants.DataConstants.ALL_CHARACTERS
            )
        )
        DataField(
            modifier = Modifier.padding(top = 8.dp),
            name = stringResource(id = R.string.menu_locations),
            value = stringResource(
                id = R.string.tv_data_with_slash,
                userProfile.countOfLocalLocations,
                Constants.DataConstants.ALL_LOCATIONS
            )
        )
        DataField(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            name = stringResource(id = R.string.menu_episodes),
            value = stringResource(
                id = R.string.tv_data_with_slash,
                userProfile.countOfLocalEpisodes,
                Constants.DataConstants.ALL_EPISODES
            )
        )
    }
}

@Composable
private fun CharacterThumb(
    character: Character
) {

    Surface(
        shape = RoundedCornerShape(8.dp),
    ) {

        ConstraintLayout(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
        ) {
            val (thumbnail, title) = createRefs()

            DefaultImage(
                photo = character.image ?: Icons.Default.Error,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Text(
                text = character.name ?: "",
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp)
                    .constrainAs(title) {
                        bottom.linkTo(thumbnail.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}