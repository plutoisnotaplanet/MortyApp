package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.domain.model.Response
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.SettingsUseCase
import javax.inject.Inject

class SettingsInteractor @Inject constructor(

): SettingsUseCase {

    override fun clearDataBase(): Response<Boolean> {
        TODO("Not yet implemented")
    }

}