package com.plutoisnotaplanet.mortyapp.ui.common.base

import androidx.annotation.StringRes

open class BaseAction {
    data class ShowStringSnack(val message: String): BaseAction()
    data class ShowResourceSnack(@StringRes val message: Int): BaseAction()
}



