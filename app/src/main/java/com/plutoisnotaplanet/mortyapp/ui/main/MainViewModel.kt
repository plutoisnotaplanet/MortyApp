package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.domain.model.onFailure
import com.plutoisnotaplanet.mortyapp.application.domain.model.onSuccess
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LaunchUseCase
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseViewModel
import com.plutoisnotaplanet.mortyapp.ui.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val launchUseCase: LaunchUseCase,
    val imageLoader: ImageLoader,
) : BaseViewModel<MainUiState>() {

    override val _uiState: MutableState<MainUiState> = mutableStateOf(MainUiState.Initialize)

    fun isLogged(): Boolean {
        return launchUseCase.isLogged
    }

    fun logout() {
        launchUseCase.logout()
    }

    fun obtainEvent(event: MainEvent) {
        when(event) {
            is MainEvent.OpenCamera -> getInputUriForPhoto()
            is MainEvent.OpenGalleryChooser -> {
                setAction(MainAction.OpenGalleryChooser)
            }
            is MainEvent.ShowResourceSnack -> {
                showSnack(event.message)
            }
            is MainEvent.ShowStringSnack -> {
                showSnack(event.message)
            }
            is MainEvent.OpenDrawerMenu -> {
                setAction(MainAction.OpenDrawerMenu)
            }
        }
    }

    private fun getInputUriForPhoto() {
        viewModelScope.launchWithCatchOnIo {
            editProfileUseCase.getInputUriForPhoto()
                .onSuccess { uri ->
                    setAction(MainAction.OpenCamera(uri))
                }
                .onFailure { error ->
                    showSnack(error.message)
                }
        }
    }

    fun savePhotoByUri(uri: Uri) {
        viewModelScope.launchWithCatchOnIo {
            editProfileUseCase.savePhotoByUri(uri)
                .onSuccess {
                    showSnack(R.string.tv_photo_was_successfully_saved)
                }
                .onFailure { error ->
                    showSnack(error.message)
                }
        }
    }

    fun loadDrawerProfile() {
        viewModelScope.launchWithCatchOnIo {
            editProfileUseCase.selfProfile()
                .distinctUntilChanged()
                .collect { profile ->
                    updateUiState(MainUiState.SelfProfileUiState(profile))
                }
        }
    }
}