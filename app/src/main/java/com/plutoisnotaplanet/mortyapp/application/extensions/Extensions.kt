package com.plutoisnotaplanet.mortyapp.application.extensions

import com.plutoisnotaplanet.mortyapp.application.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Extensions {

    fun Date?.toUiFormat(): String {
        val sdf = SimpleDateFormat(Constants.Date.DATE_FORMAT, Locale.getDefault())
        return this?.let {
            sdf.format(this)
        } ?: "Unknown"
    }
}
