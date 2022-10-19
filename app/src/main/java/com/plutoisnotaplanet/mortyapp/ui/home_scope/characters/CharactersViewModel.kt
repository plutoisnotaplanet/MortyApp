package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersUseCase: CharactersUseCase
) : ViewModel() {

    val statuses = CharacterStatus.values()
    val genders = CharacterGender.values()
    val species = CharacterSpecies.values()

    private val statusesInStringList = statuses.map { it.paramName }
    private val gendersInStringList = genders.map { it.paramName }
    val speciesInStringList = species.map { it.paramName }

    val suggestionsList = statuses.map { it } + genders + species

    private val _characters: MutableState<NetworkResponse<BaseResponse<Character>>> =
        mutableStateOf(NetworkResponse.Loading)
    val characters: State<NetworkResponse<BaseResponse<Character>>> = _characters
    val characterPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    val filtersState: MutableStateFlow<CharactersFilterModel> =
        MutableStateFlow(CharactersFilterModel())

    private val newCharactersFlow = characterPageStateFlow.combine(filtersState) { page, filter ->
        _characters.value = NetworkResponse.Loading
        page to filter
    }
        .debounce(200)
        .flatMapLatest {
            val (page, filter) = it
            Timber.e("try to request page $page")

            charactersUseCase.getCharacters(
                pageId = page,
                filterModel = filter
            )
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newCharactersFlow.collectLatest {
                _characters.value = it
            }
        }
    }

    fun fetchNextCharactersPage() {
        if (characters.value != NetworkResponse.Loading) {
            characterPageStateFlow.value++
        }
    }

    fun searchByText(filter: String) {
        resetCharacters()

        when {

            statusesInStringList.contains(filter) ->
                statuses.first { it.paramName == filter }.let { addFilter(it) }

            gendersInStringList.contains(filter) ->
                genders.first { it.paramName == filter }.let { addFilter(it) }

            speciesInStringList.contains(filter) ->
                species.first { it.paramName == filter }.let { addFilter(it) }

            else -> filtersState.value.copy(name = filter)
        }
    }

    fun addFilter(newFilter: CharacterStat?) {
        resetCharacters()
        filtersState.value = when (newFilter) {
            is CharacterStatus -> filtersState.value.copy(status = newFilter)
            is CharacterSpecies -> filtersState.value.copy(species = newFilter)
            is CharacterGender -> filtersState.value.copy(gender = newFilter)
            else -> filtersState.value
        }
    }

    fun removeFilter(filter: CharacterStat) {
        resetCharacters()
        filtersState.value = when (filter) {
            is CharacterStatus -> filtersState.value.copy(status = null)
            is CharacterSpecies -> filtersState.value.copy(species = null)
            is CharacterGender -> filtersState.value.copy(gender = null)
            else -> filtersState.value
        }
    }

    fun clearFilters() {
        resetCharacters()
        filtersState.value = CharactersFilterModel()
    }

    fun resetCharacters() {
        _characters.value = NetworkResponse.Loading
        characterPageStateFlow.value = 1
    }

}