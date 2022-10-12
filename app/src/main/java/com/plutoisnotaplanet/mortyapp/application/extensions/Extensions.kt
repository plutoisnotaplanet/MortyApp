package com.plutoisnotaplanet.mortyapp.application.extensions

import java.text.SimpleDateFormat
import java.util.*

object Extensions {

    fun Date?.toUiFormat(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return this?.let {
            sdf.format(this)
        } ?: "Unknown"
    }
}