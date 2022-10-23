package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun loadLocations(pageId: Int): Flow<NetworkResponse<List<Location>>>

    fun loadLocation(locationId: Long): Flow<NetworkResponse<Location>>
}