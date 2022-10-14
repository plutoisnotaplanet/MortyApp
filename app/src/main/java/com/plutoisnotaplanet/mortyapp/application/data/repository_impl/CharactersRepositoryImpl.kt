package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: Api
) : CharactersRepository {


    override fun loadCharacters(
        id: Int,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>> {
        return flow {

            try {

                val response = api.fetchCharacters(id)

                val charactersListDto = response.results

                emit(
                    charactersListDto.map { dto ->
                        dto.toModel()
                    }
                )
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }

    override fun loadCharacter(
        characterId: Long,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Character> {
        return flow {

            try {
                val response = api.fetchCharacter(characterId)

                emit(response.toModel())
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }

    override fun loadFilteredCharacters(
        map: Map<String,String>,
        onSuccess: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Character>> {
        return flow {

            try {

                val response = api.fetchFilteredCharacters(map)

                val charactersListDto = response.results

                emit(
                    charactersListDto.map { dto ->
                        dto.toModel()
                    }
                )
            } catch (e: Exception) {
                onError(e.message)
            }
        }
            .onCompletion { onSuccess() }
            .flowOn(Dispatchers.IO)
    }
}