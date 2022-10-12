package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: Api
): LocationRepository {


    override fun loadLocations(id: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Location>> {
        return flow {

            try {
                val response = api.fetchLocations(id)

                val locationsListDto = response.results
                emit(
                    locationsListDto.map { dto ->
                        dto.toModel()
                    }
                )
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }

    override fun loadLocation(locationId: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<Location> {
        return flow {
            try {
                val response = api.fetchLocation(locationId)

                emit(response.toModel())
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }
}