package com.plutoisnotaplanet.mortyapp.di

import android.content.Context
import coil.ImageLoader
import com.plutoisnotaplanet.mortyapp.application.utils.ConnectivityObserver
import com.plutoisnotaplanet.mortyapp.application.utils.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

}