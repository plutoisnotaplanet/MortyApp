package com.plutoisnotaplanet.mortyapp.application.domain.usecase

interface LaunchUseCase {

    val isLogged: Boolean

    fun logout()
}