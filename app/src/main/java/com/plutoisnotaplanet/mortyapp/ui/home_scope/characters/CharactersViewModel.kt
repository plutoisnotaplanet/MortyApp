package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.*
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
import com.plutoisnotaplanet.mortyapp.ui.theme.compose.SearchState
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

    private val _networkState: MutableState<NetworkResponse<Any>> = mutableStateOf(NetworkResponse.Loading, neverEqualPolicy())
    val networkState: State<NetworkResponse<Any>> = _networkState

    private val newCharactersFlow = characterPageStateFlow.combine(filtersState) { page, filter ->
        _networkState.value = NetworkResponse.Loading
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
        if (_networkState.value != NetworkResponse.Loading) {
            characterPageStateFlow.value++
        }
    }

    fun searchByText(filter: String) {
        Timber.e("search by text")
        filter.trim()
        when {
            statusesInStringList.contains(filter) ->
                addFilter(statuses.first { it.viewValue == filter })

            gendersInStringList.contains(filter) ->
                addFilter(genders.first { it.viewValue == filter })

            speciesInStringList.contains(filter) ->
                addFilter(species.first { it.viewValue == filter })

            else -> addFilter(CharacterName(apiValue = filter))
        }
    }

    fun addFilter(newFilter: CharacterStat?) {
        Timber.e("addFilter")
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
        Timber.e("removeFilter")
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
        Timber.e("update date")
        filtersState.update { filtersState.value }
    }

    fun clearFilters() {
        Timber.e("clearFilter")
        if (filtersState.value.isFiltersActive) {
            resetCharacters()
            filtersState.value = CharactersFilterModel()
        }
    }

    fun prepareSuggestionsClick(suggestion: CharacterStat) {
        Timber.e("sugget click")
        resetCharacters()
        searchByText(suggestion.viewValue)
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
                    is NetworkResponse.Success -> {
                        _characters.value.addAll(response.data)
                    }
                    is NetworkResponse.Error -> {
                        Timber.e(response.message)
                    }
                    is NetworkResponse.Loading -> {
                        Timber.e("Loading")
                    }
                }
            }
        }
    }
}