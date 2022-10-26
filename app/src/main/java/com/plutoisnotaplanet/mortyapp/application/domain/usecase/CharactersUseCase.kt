package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {

    fun getCharacters(
        pageId: Int,
        filterModel: CharactersFilterModel? = null
    ): Flow<Response<List<Character>>>

    fun getCharacterById(
        id: Long
    ): Flow<Response<Character>>

    suspend fun addOrRemoveFavoriteCharacter(
        characterId: Long
    ): Response<Unit>
}