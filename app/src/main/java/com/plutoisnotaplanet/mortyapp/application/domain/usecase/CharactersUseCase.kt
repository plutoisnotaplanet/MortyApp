package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterGender
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {

    fun getCharacters(
        pageId: Int,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>>

    fun getCharacterById(
        id: Long,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Character>

    fun filterCharacters(
        pageId: Int,
        name: String? = null,
        status: CharacterStatus? = null,
        species: String? = null,
        gender: CharacterGender? = null,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>>
}