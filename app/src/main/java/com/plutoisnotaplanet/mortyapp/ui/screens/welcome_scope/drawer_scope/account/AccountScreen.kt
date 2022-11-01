package com.plutoisnotaplanet.mortyapp.ui.screens.welcome_scope.drawer_scope.account

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.extensions.prepareSnackBars
import com.plutoisnotaplanet.mortyapp.ui.components.AnimatedButton
import com.plutoisnotaplanet.mortyapp.ui.components.BottomMenuScreen
import com.plutoisnotaplanet.mortyapp.ui.main.MainEvent
import com.plutoisnotaplanet.mortyapp.ui.screens.MenuTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    onMainEvent: (MainEvent) -> Unit = {}
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.singleAction.prepareSnackBars(onMainEvent)
    }

    val coroutineScope = rememberCoroutineScope()

    var userProfile by remember {
        mutableStateOf(UserProfile())
    }

    val uiState by viewModel.uiState

    when (uiState) {
        is AccountUiState.SelfProfileUiState -> {
            userProfile = (uiState as AccountUiState.SelfProfileUiState).userProfile
        }
        else -> {}
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
                MenuTopBar(onActionClick = { onMainEvent(MainEvent.OpenDrawerMenu) })
            }

            item {
                AccountAvatar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
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
                    when (action) {
                        BottomMenuAction.OpenGallery -> onMainEvent(MainEvent.OpenGalleryChooser)
                        BottomMenuAction.OpenCamera -> onMainEvent(MainEvent.OpenCamera)
                        BottomMenuAction.DeletePhoto -> viewModel.deleteAvatar()
                    }
                    coroutineScope.launch {
                        menuBottomSheetState.hide()
                    }
                }
            },
        ) {}
    }
}


