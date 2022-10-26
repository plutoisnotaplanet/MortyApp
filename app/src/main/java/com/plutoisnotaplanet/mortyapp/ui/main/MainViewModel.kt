package com.plutoisnotaplanet.mortyapp.ui.main

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.domain.model.onFailure
import com.plutoisnotaplanet.mortyapp.application.domain.model.onSuccess
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LaunchUseCase
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.launchOnIo
import com.plutoisnotaplanet.mortyapp.ui.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase,
    private val launchUseCase: LaunchUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    val selfProfile: MutableState<UserProfile> = mutableStateOf(UserProfile(""))

    val mainActionEvent: SingleLiveEvent<MainSingleEvent> = SingleLiveEvent()

    private val uiEvent: SingleLiveEvent<MainUiAction> = SingleLiveEvent()
    val uiEventFlow = uiEvent.asFlow()

    val isLogged: Boolean
        get() = launchUseCase.isLogged

    fun logout() {
        launchUseCase.logout()
    }

    fun handleAction(action: MainAction) {
        when(action) {
            is MainAction.OpenCamera -> getInputUriForPhoto()
            is MainAction.OpenGalleryChooser -> {
                mainActionEvent.postValue(MainSingleEvent.OpenGalleryChooser)
            }
        }
    }

    private fun getInputUriForPhoto() {
        viewModelScope.launchOnIo {
            editProfileUseCase.getInputUriForPhoto()
                .onSuccess { uri ->
                    mainActionEvent.postValue(MainSingleEvent.OpenCamera(uri))
                }
                .onFailure { error ->
                    showSnackBar(error.message)
                }
        }
    }

    fun savePhotoByUri(uri: Uri) {
        viewModelScope.launchOnIo {
            editProfileUseCase.savePhotoByUri(uri)
                .onSuccess {
                    showSnackBar(R.string.tv_photo_was_successfully_saved)
                }
                .onFailure { error ->
                    showSnackBar(error.message)
                }
        }
    }

    fun showSnackBar(message: String?) {
        if (message.isNullOrBlank()) return
        uiEvent.postValue(MainUiAction.ShowSnackBarString(message))
    }

    fun showSnackBar(messageId: Int) {
        uiEvent.postValue(MainUiAction.ShowSnackBarRes(messageId))
    }

    init {
        viewModelScope.launch {
            editProfileUseCase.selfProfile()
                .collect { profile ->
                    selfProfile.value = profile
                }
        }
    }
}