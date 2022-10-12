package com.plutoisnotaplanet.mortyapp.di

import android.content.Context
import coil.ImageLoader
import com.plutoisnotaplanet.mortyapp.application.Constants
import com.plutoisnotaplanet.mortyapp.application.data.rest.Api
import com.plutoisnotaplanet.mortyapp.application.data.rest.ApiFactory
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
object ApiModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext context: Context,
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideApi(
        connectivityObserver: ConnectivityObserver,
    ): Api =
        ApiFactory().create(
            Constants.BASE_URL,
            connectivityObserver
        )
}