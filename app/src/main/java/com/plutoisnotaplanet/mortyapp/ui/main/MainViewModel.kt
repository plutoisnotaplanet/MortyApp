package com.plutoisnotaplanet.mortyapp.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val imageLoader: ImageLoader,

    ): ViewModel() {

    private val _selectedTab: MutableState<MainScreenHomeTab> =
        mutableStateOf(MainScreenHomeTab.CHARACTERS)
    val selectedTab: State<MainScreenHomeTab> get() = _selectedTab

    fun selectTab(tab: MainScreenHomeTab) {
        _selectedTab.value = tab
    }
}