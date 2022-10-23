package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.ApiConstants
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    private val charactersRepository: CharactersRepository
) : CharactersUseCase {

    override fun getCharacters(
        pageId: Int,
        filterModel: CharactersFilterModel?,
    ): Flow<NetworkResponse<List<Character>>> {
        val map = mutableMapOf<String, String>()

        map[ApiConstants.Page] = pageId.toString()

        if (filterModel != null) {
            filterModel.gender?.let {
                map[ApiConstants.Gender] = it.apiValue
            }
            filterModel.status?.let {
                map[ApiConstants.Status] = it.apiValue
            }
            filterModel.species?.let {
                map[ApiConstants.Species] = it.apiValue
            }
            filterModel.name?.let {
                map[ApiConstants.Name] = it.apiValue
            }
        }

        return charactersRepository.loadFilteredCharacters(map)
    }

    override fun getCharacterById(
        id: Long,
    ): Flow<NetworkResponse<Character>> = charactersRepository.loadCharacter(id)
}