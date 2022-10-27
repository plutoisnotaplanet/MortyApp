package com.plutoisnotaplanet.mortyapp.ui.screens.drawer_scope.account

import androidx.lifecycle.viewModelScope
import com.plutoisnotaplanet.mortyapp.application.domain.model.BottomMenuAction
import com.plutoisnotaplanet.mortyapp.application.domain.model.onFailure
import com.plutoisnotaplanet.mortyapp.application.domain.model.onSuccess
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.EditProfileUseCase
import com.plutoisnotaplanet.mortyapp.application.extensions.Extensions.launchOnIo
import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) : BaseViewModel() {

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

    fun deleteAvatar() {
        viewModelScope.launchOnIo {
            editProfileUseCase.deleteAvatar()
                .onFailure { error ->
                    Timber.e("$error")
                }
                .onSuccess { Timber.e("success") }
        }
    }

    fun clearDataBase() {
        viewModelScope.launchOnIo {
            editProfileUseCase.clearDataBase()
                .onFailure { error ->
                    showSnackSuspend(error.message)
                }
                .onSuccess {
                    showSnackSuspend("Database cleared")
                }
        }
    }

    init {
        viewModelScope.launch {
            editProfileUseCase.selfProfile()
                .distinctUntilChanged()
                .collectLatest { profile ->
                    setStateSuspend(AccountUiViewState.SelfProfileViewState(profile))
                }
        }
    }
}