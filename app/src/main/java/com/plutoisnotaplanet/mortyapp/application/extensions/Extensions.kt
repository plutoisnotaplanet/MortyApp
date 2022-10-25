package com.plutoisnotaplanet.mortyapp.application.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Extensions {

    fun Date?.toUiFormat(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return this?.let {
            sdf.format(this)
        } ?: "Unknown"
    }

    fun CoroutineScope.launchOnIo(block: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO) {
        block()
    }
}