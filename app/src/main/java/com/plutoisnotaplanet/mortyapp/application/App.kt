package com.plutoisnotaplanet.mortyapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Timber.plant(Timber.DebugTree())

    }
}