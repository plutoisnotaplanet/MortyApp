package com.plutoisnotaplanet.mortyapp.di

import com.plutoisnotaplanet.mortyapp.application.data.interactors.CharactersInteractor
import com.plutoisnotaplanet.mortyapp.application.domain.usecase.CharactersUseCase
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
}