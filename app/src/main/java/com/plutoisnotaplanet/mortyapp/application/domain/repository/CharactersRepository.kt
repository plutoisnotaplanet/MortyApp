package com.plutoisnotaplanet.mortyapp.application.domain.repository

import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun loadCharacters(id: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<List<Character>>

    fun loadCharacter(characterId: Long, onSuccess: () -> Unit, onError: (String?) -> Unit): Flow<Character>
}