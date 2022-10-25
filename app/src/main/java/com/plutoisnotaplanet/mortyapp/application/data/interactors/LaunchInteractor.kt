package com.plutoisnotaplanet.mortyapp.application.data.interactors

import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.Preferences
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.LaunchUseCase
import javax.inject.Inject

class LaunchInteractor @Inject constructor(
    private val preferences: Preferences
) : LaunchUseCase {

    override val isLogged: Boolean
        get() = preferences.isLogged

    override fun logout() {
        preferences.logout()
    }
}