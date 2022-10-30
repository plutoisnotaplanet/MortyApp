package com.plutoisnotaplanet.mortyapp.application.extensions

import com.plutoisnotaplanet.mortyapp.ui.common.base.BaseAction
import com.plutoisnotaplanet.mortyapp.ui.main.MainAction
import com.plutoisnotaplanet.mortyapp.ui.main.MainEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun CoroutineScope.launchOnIo(block: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO) {
    block()
}

suspend inline fun SharedFlow<BaseAction>.prepareSnackBars(crossinline onMainEvent: (MainEvent) -> Unit) {
    this.collectLatest { action ->
        when(action) {
            is BaseAction.ShowResourceSnack -> onMainEvent(MainEvent.ShowResourceSnack(action.message))
            is BaseAction.ShowStringSnack -> onMainEvent(MainEvent.ShowStringSnack(action.message))
            else -> Unit
        }
    }
}

