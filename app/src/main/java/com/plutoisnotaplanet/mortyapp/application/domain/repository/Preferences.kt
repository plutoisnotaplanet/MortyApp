package com.plutoisnotaplanet.mortyapp.application.domain.repository

interface Preferences {

    var userId: Long

    var login: String

    var password: String

    var isLogged: Boolean

    fun logout()
}