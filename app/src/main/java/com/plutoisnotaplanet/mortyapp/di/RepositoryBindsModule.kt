package com.plutoisnotaplanet.mortyapp.di

import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.*
import com.plutoisnotaplanet.mortyapp.application.domain.repository.AuthRepository
import com.plutoisnotaplanet.mortyapp.application.domain.repository.CharactersRepository
import com.plutoisnotaplanet.mortyapp.application.domain.repository.EpisodeRepository
import com.plutoisnotaplanet.mortyapp.application.domain.repository.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryBindsModule {

    @Binds
    @ViewModelScoped
    fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    @ViewModelScoped
    fun bindEpisodeRepository(episodeRepositoryImpl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    @ViewModelScoped
    fun bindCharacterRepository(charactersRepositoryImpl: CharactersRepositoryImpl): CharactersRepository

    @Binds
    @ViewModelScoped
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @ViewModelScoped
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}