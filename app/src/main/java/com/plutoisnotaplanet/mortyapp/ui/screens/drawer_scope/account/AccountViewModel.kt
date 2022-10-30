package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.R
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.onFailure
import com.plutoisnotaplanet.mortyapp.application.domain.model.onSuccess
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) : BaseViewModel<AccountUiState>() {

    companion object {
        val bottomMenuList = BottomMenuAction.values().toList()
    }

    override val _uiState: MutableState<AccountUiState> = mutableStateOf(AccountUiState.Initialize)

    inline val menuActionsList: (Boolean) -> List<BottomMenuAction>
        get() = { hasAvatar ->
            if (hasAvatar) {
                bottomMenuList
            } else {
                bottomMenuList.filter { it != BottomMenuAction.DeletePhoto }
            }
        }

    fun deleteAvatar() {
        viewModelScope.launchWithCatchOnIo {
            editProfileUseCase.deleteAvatar()
                .onFailure { error ->
                    showSnack(error.message)
                }
                .onSuccess { Timber.e("success") }
        }
    }

    fun clearDataBase() {
        viewModelScope.launchWithCatchOnIo {
            editProfileUseCase.clearDataBase()
                .onFailure { error ->
                    showSnack(error.message)
                }
                .onSuccess {
                    showSnack(R.string.tv_success_db_clear)
                }
        }
    }

    init {
        viewModelScope.launch {
            editProfileUseCase.selfProfile()
                .distinctUntilChanged()
                .collectLatest { profile ->
                    updateUiState(AccountUiState.SelfProfileUiState(profile))
                }
        }
    }

}