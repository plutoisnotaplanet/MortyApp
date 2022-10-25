package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource

sealed class SnackBarMessage {

    object EmptyMessage: SnackBarMessage()

    data class ResMessage(@StringRes val messageRes: Int): SnackBarMessage()

    data class StringMessage(val message: String): SnackBarMessage()
}

@Composable
fun SnackBarMessage.asString(): String {
    return when(this) {
        is SnackBarMessage.ResMessage -> stringResource(id = this.messageRes)
        is SnackBarMessage.StringMessage -> this.message
        else -> ""
    }
}