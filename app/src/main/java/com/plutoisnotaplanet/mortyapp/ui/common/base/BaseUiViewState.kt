package com.plutoisnotaplanet.mortyapp.ui.common.base

import com.plutoisnotaplanet.mortyapp.ui.main.MainAction
import timber.log.Timber

abstract class BaseUiViewState {
    object Initialize : BaseUiViewState()
    object Loading : BaseUiViewState()
    object Success : BaseUiViewState()
    data class Error(val message: String) : BaseUiViewState()
    data class ShowStringSnack(val message: String): BaseUiViewState()
    data class ShowResourceSnack(val message: Int): BaseUiViewState()
}

inline fun BaseUiViewState.loadSnackBar(onMainAction: (MainAction) -> Unit) {
    when (this) {
        is BaseUiViewState.ShowStringSnack -> {
            onMainAction(MainAction.ShowStringSnack(message))
        }
        is BaseUiViewState.ShowResourceSnack -> {
            onMainAction(MainAction.ShowResourceSnack(message))
        }
    }
}