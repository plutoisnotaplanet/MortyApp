package com.plutoisnotaplanet.mortyapp.ui.home_scope.locations

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
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

    private val _networkState: MutableState<NetworkResponse<Any>> = mutableStateOf(NetworkResponse.Loading, neverEqualPolicy())
    val networkState: State<NetworkResponse<Any>> = _networkState

    private val newLocationsFlow = locationPageStateFlow
        .flatMapLatest { page ->
            locationsUseCase.getLocations(
                pageId = page
            )
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    fun fetchNextLocationsPage() {
        if (_networkState.value != NetworkResponse.Loading) {
            locationPageStateFlow.value++
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newLocationsFlow.collectLatest { response ->
                _networkState.value = response
                when (response) {
                    is NetworkResponse.Success -> {
                        _locations.value.addAll(response.data)
                        _locations.value = _locations.value
                    }
                    is NetworkResponse.Error -> {
                        Timber.e(response.message)
                    }
                    is NetworkResponse.Loading -> {
                        Timber.e("Loading")
                    }
                }
            }
        }
    }
}