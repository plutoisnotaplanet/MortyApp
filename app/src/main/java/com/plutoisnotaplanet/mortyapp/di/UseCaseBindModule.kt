package com.plutoisnotaplanet.mortyapp.di

import com.plutoisnotaplanet.mortyapp.application.data.interactors.*
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseBindModule {

    @Binds
    @ViewModelScoped
    fun bindCharacterUseCaseToCharacterInteractor(charactersInteractor: CharactersInteractor): CharactersUseCase

    @Binds
    @ViewModelScoped
    fun bindLocationUseCaseToLocationInteractor(locationsInteractor: LocationsInteractor): LocationsUseCase

    @Binds
    @ViewModelScoped
    fun bindLaunchUseCaseToLaunchInteractor(launchInteractor: LaunchInteractor): LaunchUseCase

    @Binds
    @ViewModelScoped
    fun bindAuthUseCaseToAuthInteractor(authInteractor: AuthInteractor): AuthUseCase

    @Binds
    @ViewModelScoped
    fun bindEditProfileUseCaseToEditProfileInteractor(editProfileInteractor: EditProfileInteractor): EditProfileUseCase
}