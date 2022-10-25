package com.plutoisnotaplanet.mortyapp.application.domain.model

import androidx.annotation.StringRes

sealed class InputState {

    object Initialize: InputState()

    object Valid: InputState()

    data class Error(@StringRes val errorRes: Int): InputState()
}