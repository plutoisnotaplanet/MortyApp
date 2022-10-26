package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharactersFilterModel
import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun loadCharacters(pageId: Int): Flow<Response<List<Character>>>

    fun loadCharacter(characterId: Long): Flow<Response<Character>>

    fun loadFilteredCharactersRemote(
        map: Map<String, String>
    ): Flow<Response<List<Character>>>

    fun loadFilteredCharactersLocal(
        pageId: Int,
        filterModel: CharactersFilterModel?
    ): Flow<Response<List<Character>>>

    suspend fun addOrRemoveFavoriteCharacter(characterId: Long)
}