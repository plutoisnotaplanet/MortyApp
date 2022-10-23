package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import com.plutoisnotaplanet.mortyapp.application.domain.repository.LocationRepository
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LocationsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsInteractor @Inject constructor(
    private val locationRepository: LocationRepository
): LocationsUseCase {

    override fun getLocations(pageId: Int): Flow<NetworkResponse<List<Location>>> =
        locationRepository.loadLocations(pageId)

    override fun getCharacterById(id: Long): Flow<NetworkResponse<Location>> =
        locationRepository.loadLocation(id)

}