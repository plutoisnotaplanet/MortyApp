package com.plutoisnotaplanet.mortyapp.ui.drawer_scope

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile
import com.plutoisnotaplanet.mortyapp.application.domain.model.onFailure
import com.plutoisnotaplanet.mortyapp.application.domain.model.onSuccess
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.launchOnIo
import com.plutoisnotaplanet.mortyapp.ui.common.delegate.MortyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) : MortyViewModel<AccountUiState>() {

    companion object {
        val bottomMenuList = BottomMenuAction.values().toList()
    }

    val menuActionsList: (Boolean) -> List<BottomMenuAction>
        get() = { hasAvatar ->
            if (hasAvatar) {
                bottomMenuList
            } else {
                bottomMenuList.filter { it != BottomMenuAction.DeletePhoto }
            }
        }

    val selfProfile = mutableStateOf(UserProfile(""))

    override val _uiState: MutableState<AccountUiState> = mutableStateOf(AccountUiState.Initialize)

    fun deleteAvatar() {
        viewModelScope.launchOnIo {
            editProfileUseCase.deleteAvatar()
                .onFailure { error ->
                    Timber.e("$error")
                }
                .onSuccess { Timber.e("success") }
        }
    }

    init {
        viewModelScope.launch {
            editProfileUseCase.selfProfile()
                .catch { e ->
                    Timber.e(e)
                }
                .collectLatest { profile ->
                    Timber.e("${profile.photoData}")
                    selfProfile.value = profile
                }
        }
    }
}