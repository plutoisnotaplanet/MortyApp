package com.plutoisnotaplanet.mortyapp.ui.home_scope.locations

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LocationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationsUseCase: LocationsUseCase
): ViewModel() {

    private val _locations: MutableState<MutableList<Location>> = mutableStateOf(mutableListOf(), neverEqualPolicy())
    val locations: State<MutableList<Location>> = _locations

    val locationPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _networkState: MutableState<Response<Any>> = mutableStateOf(Response.Loading, neverEqualPolicy())
    val networkState: State<Response<Any>> = _networkState

    private val newLocationsFlow = locationPageStateFlow
        .flatMapLatest { page ->
            locationsUseCase.getLocations(
                pageId = page
            )
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    fun fetchNextLocationsPage() {
        if (_networkState.value != Response.Loading) {
            locationPageStateFlow.value++
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newLocationsFlow.collectLatest { response ->
                _networkState.value = response
                when (response) {
                    is Response.Success -> {
                        _locations.value.addAll(response.data)
                        _locations.value = _locations.value
                    }
                    is Response.Error -> {
                        Timber.e(response.error.message)
                    }
                    is Response.Loading -> {
                        Timber.e("Loading")
                    }
                }
            }
        }
    }
}