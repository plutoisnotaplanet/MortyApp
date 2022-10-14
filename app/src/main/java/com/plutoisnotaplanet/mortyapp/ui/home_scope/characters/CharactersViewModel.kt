package com.plutoisnotaplanet.mortyapp.ui.home_scope.characters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.NetworkState
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

    private val _charactersLoadingState: MutableState<NetworkState> =
        mutableStateOf(NetworkState.IDLE)
    val charactersLoadingState: State<NetworkState> get() = _charactersLoadingState

    val characters: State<MutableList<Character>> = mutableStateOf(mutableListOf())
    val characterPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    private val newCharactersFlow = characterPageStateFlow.flatMapLatest {
        Timber.e("loading page $it")
        _charactersLoadingState.value = NetworkState.LOADING
        charactersUseCase.getCharacters(
            pageId = it,
            onSuccess = { _charactersLoadingState.value = NetworkState.SUCCESS },
            onError = { _charactersLoadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newCharactersFlow.collectLatest {
                characters.value.addAll(it)
            }
        }
    }

    fun fetchNextCharactersPage() {
        if (charactersLoadingState.value != NetworkState.LOADING) {
            characterPageStateFlow.value++
        }
    }
}