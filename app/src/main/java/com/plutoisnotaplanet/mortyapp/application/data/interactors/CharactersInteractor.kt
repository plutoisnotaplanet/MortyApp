package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.ApiConstants
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterGender
import com.plutoisnotaplanet.mortyapp.application.domain.model.CharacterStatus
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    private val charactersRepository: CharactersRepository
) : CharactersUseCase {

    override fun getCharacters(
        pageId: Int,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>> = charactersRepository.loadCharacters(pageId, onSuccess, onError)

    override fun getCharacterById(
        id: Long,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Character> = charactersRepository.loadCharacter(id, onSuccess, onError)

    override fun filterCharacters(
        pageId: Int,
        name: String?,
        status: CharacterStatus?,
        species: String?,
        gender: CharacterGender?,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>> {

        val map = mutableMapOf<String, String>()

        name?.let { map[ApiConstants.Name] = it }
        status?.let { map[ApiConstants.Status] = it.status }
        species?.let { map[ApiConstants.Species] = it }
        gender?.let { map[ApiConstants.Gender] = it.gender }

        return charactersRepository.loadFilteredCharacters(map, onSuccess, onError)
    }
}