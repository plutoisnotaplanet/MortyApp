package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: Api
): LocationRepository {


    override fun loadLocations(pageId: Int): Flow<Response<List<Location>>> {
        return flow {

            try {
                val response = api.fetchLocations(pageId)

                val locationsListDto = response.results
                emit(
                    Response.Success(
                        locationsListDto.map { dto ->
                            dto.toModel()
                        }
                    )
                )
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
            .flowOn(Dispatchers.IO)
    }

    override fun loadLocation(locationId: Long): Flow<Response<Location>> {
        return flow {
            try {
                val response = api.fetchLocation(locationId)

                emit(Response.Success(response.toModel()))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
            .flowOn(Dispatchers.IO)
    }
}