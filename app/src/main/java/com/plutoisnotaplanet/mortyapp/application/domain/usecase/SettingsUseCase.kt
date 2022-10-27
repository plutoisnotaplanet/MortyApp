package com.plutoisnotaplanet.mortyapp.application.domain.usecase

import com.plutoisnotaplanet.mortyapp.application.domain.model.Response

interface SettingsUseCase {

    fun clearDataBase(): Response<Boolean>

}