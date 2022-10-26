package com.plutoisnotaplanet.mortyapp.application.data.repository_impl

import com.plutoisnotaplanet.mortyapp.application.data.database.DbFindHelper
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dbFindHelper: DbFindHelper,
    private val preferences: MortyPreferences
) : CharactersRepository {

    private val dataBase: MortyDataBase
        get() = dbFindHelper.getDatabase()

    override suspend fun addOrRemoveFavoriteCharacter(characterId: Long) {

        val character = dataBase.charactersDao.getCharacterById(characterId)
        character.isFavorite = !character.isFavorite
        dataBase.charactersDao.save(character)

        val selfProfile = dataBase.userProfileDao.getProfileByEmail(preferences.email)

        selfProfile.copy(
            favoriteCharactersList = selfProfile.favoriteCharactersList?.apply {
                if (contains(character.toModel())) {
                    remove(character.toModel())
                } else {
                    add(character.toModel())
                }
            }
        ).let {
            dataBase.userProfileDao.save(it)
        }
    }

    override fun loadCharacters(pageId: Int): Flow<Response<List<Character>>> {
        return flow {
            try {
                var characters =
                    dataBase.charactersDao.getCharacterListByPage(pageId).map { it.toModel() }
                if (characters.isEmpty()) {
                    val response = api.fetchCharacters(pageId)
                    val charactersListDto = response.results
                    characters = charactersListDto.map { it.toModel() }
                    dataBase.charactersDao.insertAll(charactersListDto.map { it.toDbEntity(pageId) })
                }

                emit(
                    success(characters)
                )
            } catch (e: Exception) {
                emit(error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun loadCharacter(
        characterId: Long
    ): Flow<Response<Character>> {
        return flow {

            try {
                val response = api.fetchCharacter(characterId)

                emit(
                    Response.Success(
                        data = response.toModel(),
                    )
                )
            } catch (e: Exception) {
                emit(
                    Response.Error(
                        error = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun loadFilteredCharactersRemote(
        map: Map<String, String>,
    ): Flow<Response<List<Character>>> = flow {
        try {

            val response = api.fetchFilteredCharacters(map)

            val charactersListDto = response.results

            emit(Response.Success(data = charactersListDto.map { dto ->
                dto.toModel()
            }))
        } catch (e: Exception) {
            emit(
                Response.Error(e)
            )
        }

    }.flowOn(Dispatchers.IO)

    override fun loadFilteredCharactersLocal(pageId: Int, filterModel: CharactersFilterModel?): Flow<Response<List<Character>>> {
        return flow {

            val startIndex = pageId * 20
            try {
                requireNotNull(filterModel)
                var characters =
                    dataBase.charactersDao.getAllCharacters().map { it.toModel() }

                characters = when {
                    filterModel.species != null -> {
                        characters.filter { it.species == filterModel.species }
                    }
                    filterModel.gender != null -> {
                        characters.filter { it.gender == filterModel.gender }
                    }
                    filterModel.status != null -> {
                        characters.filter { it.status == filterModel.status }
                    }
                    filterModel.name != null -> {
                        characters.filter { it.name == filterModel.name.apiValue }
                    }
                    else -> characters
                }

                characters = characters.subList(startIndex, startIndex + 20)

                emit(success(characters))
            } catch (e: Exception) {
                emit(error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}