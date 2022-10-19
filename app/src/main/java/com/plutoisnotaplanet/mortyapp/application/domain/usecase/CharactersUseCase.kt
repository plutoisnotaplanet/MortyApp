package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {

    fun getCharacters(
        pageId: Int,
        filterModel: CharactersFilterModel? = null
    ): Flow<NetworkResponse<BaseResponse<Character>>>

    fun getCharacterById(
        id: Long
    ): Flow<NetworkResponse<Character>>

}