package com.plutoisnotaplanet.mortyapp.ui.screens.home_scope.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import com.plutoisnotaplanet.mortyapp.ui.components.SearchState
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

    val statuses: List<CharacterStat> = CharacterStatus.values().toList()
    val genders: List<CharacterStat> = CharacterGender.values().toList()
    val species: List<CharacterStat> = CharacterSpecies.values().toList()

    private val statusesInStringList = statuses.map { it.viewValue }
    private val gendersInStringList = genders.map { it.viewValue }
    private val speciesInStringList = species.map { it.viewValue }

    private val _searchState: MutableState<SearchState<CharacterStat>> = mutableStateOf(
        SearchState(statuses + genders + species)
    )
    val searchState: State<SearchState<CharacterStat>> = _searchState

    private val _characters: MutableState<MutableList<Character>> = mutableStateOf(mutableListOf(), neverEqualPolicy())
    val characters: State<MutableList<Character>> = _characters

    val filtersState: MutableStateFlow<CharactersFilterModel> =
        MutableStateFlow(CharactersFilterModel())

    val characterPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _networkState: MutableState<Response<Any>> = mutableStateOf(Response.Loading, neverEqualPolicy())
    val networkState: State<Response<Any>> = _networkState

    private val newCharactersFlow = characterPageStateFlow.combine(filtersState) { page, filter ->
        _networkState.value = Response.Loading
        Timber.e("trigger ${page} ${filter}")
        page to filter
    }
        .distinctUntilChanged()
        .flatMapLatest {
            val (page, filter) = it
            Timber.e("try to request page $page")
            charactersUseCase.getCharacters(
                pageId = page,
                filterModel = filter
            )
        }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)


    fun fetchNextCharactersPage() {
        Timber.e("fetch next")
        if (_networkState.value != Response.Loading) {
            characterPageStateFlow.value++
        }
    }

    fun searchByText(filter: String) {
        val trimmedText = filter.trim()
        when {
            statusesInStringList.contains(trimmedText) ->
                addFilter(statuses.first { it.viewValue == filter })

            gendersInStringList.contains(trimmedText) ->
                addFilter(genders.first { it.viewValue == filter })

            speciesInStringList.contains(trimmedText) ->
                addFilter(species.first { it.viewValue == filter })

            else -> addFilter(CharacterName(apiValue = filter))
        }
    }

    fun addFilter(newFilter: CharacterStat?) {
        resetCharacters()
        filtersState.value = when (newFilter) {
            is CharacterStatus -> filtersState.value.copy(status = newFilter)
            is CharacterSpecies -> filtersState.value.copy(species = newFilter)
            is CharacterGender -> filtersState.value.copy(gender = newFilter)
            is CharacterName -> filtersState.value.copy(name = newFilter)
            else -> filtersState.value
        }
    }

    fun removeFilter(filter: CharacterStat) {
        resetCharacters()
        filtersState.value = when (filter) {
            is CharacterStatus -> filtersState.value.copy(status = null)
            is CharacterSpecies -> filtersState.value.copy(species = null)
            is CharacterGender -> filtersState.value.copy(gender = null)
            is CharacterName -> filtersState.value.copy(name = null)
            else -> filtersState.value
        }
    }

    fun updateData() {
        filtersState.update { filtersState.value }
    }

    fun clearFilters() {
        if (filtersState.value.isFiltersActive) {
            resetCharacters()
            filtersState.value = CharactersFilterModel()
        }
    }

    fun prepareSuggestionsClick(suggestion: CharacterStat) {
        resetCharacters()
        searchByText(suggestion.viewValue)
    }

    fun addOrRemoveFromFavorites(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            charactersUseCase.addOrRemoveFavoriteCharacter(character)
                .onSuccess { Timber.e("added $character") }
                .onFailure { Timber.e("${it.message}") }
        }
    }

    private fun resetCharacters() {
        characterPageStateFlow.update { 1 }
        characters.value.clear()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newCharactersFlow.collectLatest { response ->
                _networkState.value = response
                when (response) {
                    is Response.Success -> {
                        _characters.value.addAll(response.data)
                        _characters.value = _characters.value
                    }
                    is Response.Error -> {
                        Timber.e(response.error.message)
                    }
                    is Response.Loading -> {
                        Timber.e("Loading")
                    }
                }
            }
        }
    }
}