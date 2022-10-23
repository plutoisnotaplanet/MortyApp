package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface LocationsUseCase {

    fun getLocations(
        pageId: Int
    ): Flow<NetworkResponse<List<Location>>>

    fun getCharacterById(
        id: Long
    ): Flow<NetworkResponse<Location>>

}