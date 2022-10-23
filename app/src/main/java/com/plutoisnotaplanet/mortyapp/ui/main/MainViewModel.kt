package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val launchUseCase: LaunchUseCase,
    val imageLoader: ImageLoader,
) : ViewModel() {

    val isLogged: Boolean
        get() = launchUseCase.isLogged

    fun logout() {
        launchUseCase.logout()
    }

}