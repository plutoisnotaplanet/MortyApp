package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun loadCharacters(id: Int, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Character>>

    fun loadCharacter(characterId: Long): Flow<NetworkResponse<Character>>

    fun loadFilteredCharacters(
        map: Map<String, String>
    ): Flow<NetworkResponse<List<Character>>>
}