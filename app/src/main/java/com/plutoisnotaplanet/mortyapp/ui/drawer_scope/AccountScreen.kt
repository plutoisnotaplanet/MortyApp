package com.plutoisnotaplanet.mortyapp.ui.drawer_scope

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.utils.compose.AnimatedButton
import com.plutoisnotaplanet.mortyapp.ui.common.delegate.BottomMenuScreen
import com.plutoisnotaplanet.mortyapp.ui.main.MainAction
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.AvatarImage
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.DefaultImage
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.ElevationColumn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    onMainAction: (MainAction) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()

    val uiState by viewModel.uiState
    val userProfile by viewModel.selfProfile

    val menuBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxWidth()) {

            ConstraintLayout(modifier = modifier.fillMaxWidth()) {

                val (profileImage, addPhotoBtn) = createRefs()

                AvatarImage(
                    modifier = Modifier
                        .constrainAs(profileImage) {
                            top.linkTo(parent.top, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    avatar = userProfile.photoData?.photoUri ?: Icons.Filled.AccountCircle,
                    onClick = { coroutineScope.launch { menuBottomSheetState.show() } }
                )

                Image(
                    modifier = Modifier
                        .clickable { coroutineScope.launch { menuBottomSheetState.show() } }
                        .constrainAs(addPhotoBtn) {
                            top.linkTo(profileImage.bottom, margin = (-16).dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    painter = painterResource(id = R.drawable.ic_btn_add_photo),
                    contentDescription = null,
                )
            }

            ElevationColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "E-mail:",
                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                )
                Text(
                    text = userProfile.email ?: "No email",
                    style = MaterialTheme.typography.body1,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }

            if (!userProfile.favoriteCharactersList.isNullOrEmpty()) {

                ElevationColumn(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = "Favorites characters:",
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 18.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 12.dp, bottom = 8.dp, top = 8.dp)
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

            AnimatedButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Change E-mail",
                backgroundColor = Color.White,
                textColor = Color.DarkGray
            ) {

            }

            AnimatedButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Change password",
                backgroundColor = Color.White,
                textColor = Color.DarkGray
            ) {

            }

        }

        AnimatedButton(
            modifier = Modifier.padding(bottom = 24.dp).align(Alignment.BottomCenter),
            text = "Delete account",
            textColor = Color.White,
            backgroundColor = colorResource(id = R.color.colorPrimary)
        ) {

        }

        ModalBottomSheetLayout(
            modifier = Modifier.align(Alignment.BottomCenter),
            sheetState = menuBottomSheetState,
            sheetElevation = 16.dp,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            sheetContent = {
                BottomMenuScreen(
                    actionsList = viewModel.menuActionsList(userProfile.photoData?.photoUri != null)
                ) { action ->
                    coroutineScope.launch {
                        menuBottomSheetState.hide()
                    }
                    when (action) {
                        BottomMenuAction.OpenGallery -> onMainAction(MainAction.OpenGalleryChooser)
                        BottomMenuAction.OpenCamera -> onMainAction(MainAction.OpenCamera)
                        BottomMenuAction.DeletePhoto -> viewModel.deleteAvatar()
                    }
                }
            },
        ) {}
    }
}

@Composable
private fun CharacterThumb(
    character: Character
) {

    Surface(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
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
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(24.dp)
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
