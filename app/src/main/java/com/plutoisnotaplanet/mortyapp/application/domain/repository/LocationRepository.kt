package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun loadLocations(id: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Location>>

    fun loadLocation(locationId: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<Location>
}