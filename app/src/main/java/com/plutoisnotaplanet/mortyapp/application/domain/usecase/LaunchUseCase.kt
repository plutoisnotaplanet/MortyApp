package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.PasswordValidationState
import kotlinx.coroutines.flow.Flow

interface LaunchUseCase {

    val isLogged: Boolean

    fun logout()
}