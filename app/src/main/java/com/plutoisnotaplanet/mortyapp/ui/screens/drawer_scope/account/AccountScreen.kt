package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseUiViewState
import com.plutoisnotaplanet.mortyapp.ui.common.base.loadSnackBar
import com.plutoisnotaplanet.mortyapp.ui.components.AnimatedButton
import com.plutoisnotaplanet.mortyapp.ui.common.delegate.BottomMenuScreen
import com.plutoisnotaplanet.mortyapp.ui.main.MainAction
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    onMainAction: (MainAction) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    var userProfile by remember {
        mutableStateOf(UserProfile())
    }

    val uiState by viewModel.uiState
    uiState.loadSnackBar(onMainAction)

    when (uiState) {
        is AccountUiViewState.SelfProfileViewState -> {
            userProfile = (uiState as AccountUiViewState.SelfProfileViewState).userProfile
        }
    }

    val menuBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    val clearDataBaseDialogState = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            item {
                AccountAvatar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp),
                    userProfile = userProfile,
                    onClick = { coroutineScope.launch { menuBottomSheetState.show() } }
                )
            }

            item {
                AccountData(
                    userProfile = userProfile
                )
            }

            item {
                AnimatedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.tt_change_email),
                    backgroundColor = Color.White,
                    textColor = Color.DarkGray
                ) {

                }

                AnimatedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.tt_change_password),
                    backgroundColor = Color.White,
                    textColor = Color.DarkGray
                ) {

                }

                if (userProfile.isNotEmpty) {
                    AnimatedButton(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(R.string.tt_clear_database),
                        backgroundColor = Color.White,
                        textColor = Color.DarkGray,
                        onClick = { clearDataBaseDialogState.value = true }
                    )
                }



                AnimatedButton(
                    modifier = Modifier
                        .padding(top = 64.dp, bottom = 16.dp)
                        .align(Alignment.BottomCenter),
                    text = stringResource(R.string.tt_delete_account),
                    textColor = Color.White,
                    backgroundColor = colorResource(id = R.color.colorPrimary)
                ) {

                }
            }
        }

        ClearDataBaseDialog(
            state = clearDataBaseDialogState,
            onClearClick = viewModel::clearDataBase
        )

        ModalBottomSheetLayout(
            modifier = Modifier.align(Alignment.BottomCenter),
            sheetState = menuBottomSheetState,
            sheetElevation = 16.dp,
            sheetBackgroundColor = Color.White,
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


