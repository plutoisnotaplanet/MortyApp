package com.plutoisnotaplanet.mortyapp.ui.common.base

import com.plutoisnotaplanet.mortyapp.ui.main.MainEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

suspend inline fun SharedFlow<BaseAction>.prepareSnackBars(crossinline onMainEvent: (MainEvent) -> Unit) {
    this.collectLatest { action ->
        when(action) {
            is BaseAction.ShowResourceSnack -> onMainEvent(MainEvent.ShowResourceSnack(action.message))
            is BaseAction.ShowStringSnack -> onMainEvent(MainEvent.ShowStringSnack(action.message))
            else -> Unit
        }
    }
}