package com.plutoisnotaplanet.mortyapp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.Preferences
import com.plutoisnotaplanet.mortyapp.application.utils.ValidationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context) = Preferences(context)

    @Singleton
    @Provides
    fun provideValidationUtil() = ValidationUtil

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}