package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun loadEpisodes(id: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Episode>>

    fun loadEpisode(episodeId: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<Episode>
}