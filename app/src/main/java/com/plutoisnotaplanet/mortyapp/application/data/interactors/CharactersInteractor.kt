package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.ApiConstants
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val preferences: MortyPreferences
) : CharactersUseCase {

    override fun getCharacters(
        pageId: Int,
        filterModel: CharactersFilterModel?,
    ): Flow<Response<List<Character>>> {

        return when {
            filterModel?.isFiltersActive == false ->
                charactersRepository.loadCharacters(pageId)

            preferences.isFiltersLocal -> {
                charactersRepository.loadFilteredCharactersLocal(pageId, filterModel!!)
            }

            else -> {
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

                charactersRepository.loadFilteredCharactersRemote(map)
            }
        }
    }

    override suspend fun addOrRemoveFavoriteCharacter(character: Character): Response<Unit> {
        return runResulting {
            charactersRepository.addOrRemoveFavoriteCharacter(character)
        }
    }

    override fun getCharacterById(
        id: Long,
    ): Flow<Response<Character>> = charactersRepository.loadCharacter(id)
}