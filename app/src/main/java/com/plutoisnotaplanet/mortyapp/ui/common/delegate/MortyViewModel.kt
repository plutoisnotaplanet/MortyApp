package com.plutoisnotaplanet.mortyapp.ui.common.delegate

import androidx.lifecycle.ViewModel
import com.plutoisnotaplanet.mortyapp.application.domain.model.SnackBarMessage
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class MortyViewModel: ViewModel() {

    private val _snackBarMessage: Channel<SnackBarMessage> = Channel(
        capacity = Channel.RENDEZVOUS,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val snackBarMessage: Flow<SnackBarMessage> = _snackBarMessage.consumeAsFlow().distinctUntilChanged()

    fun showSnack(message: Int) {
        _snackBarMessage.trySend(SnackBarMessage.ResMessage(message))
    }

    fun showSnack(message: String?) {
        if (message != null) {
            _snackBarMessage.trySend(SnackBarMessage.StringMessage(message))
        }
    }
}