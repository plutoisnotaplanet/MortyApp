package com.plutoisnotaplanet.mortyapp.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.plutoisnotaplanet.mortyapp.application.data.database.DbFindHelper
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import com.plutoisnotaplanet.mortyapp.application.utils.BitmapFactory
import com.plutoisnotaplanet.mortyapp.application.utils.CompressFileHelper
import com.plutoisnotaplanet.mortyapp.application.utils.FileManager
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
    fun providePreferences(@ApplicationContext context: Context) = MortyPreferences(context)

    @Singleton
    @Provides
    fun provideValidationUtil() = ValidationUtil

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFileManager(@ApplicationContext context: Context) = FileManager(context)

    @Singleton
    @Provides
    fun provideDbFindHelper(@ApplicationContext context: Context, preferences: MortyPreferences) =
        DbFindHelper(context, preferences)

    @Singleton
    @Provides
    fun provideCompressFileHelper(@ApplicationContext context: Context) =
        CompressFileHelper(context)

    @Singleton
    @Provides
    fun provideBitmapFactory(@ApplicationContext context: Context) = BitmapFactory(context)
}