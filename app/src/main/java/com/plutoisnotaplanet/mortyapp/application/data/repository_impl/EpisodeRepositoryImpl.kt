package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.domain.model.Episode
import com.plutoisnotaplanet.mortyapp.application.domain.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: Api
): EpisodeRepository {

    override fun loadEpisodes(id: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Episode>> {
        return flow {

            try {
                val response = api.fetchEpisodes(id)

                val episodesListDto = response.results
                emit(
                    episodesListDto.map { dto ->
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

    override fun loadEpisode(episodeId: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<Episode> {
        return flow {
            try {
                val response = api.fetchEpisode(episodeId)

                emit(response.toModel())
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }
}