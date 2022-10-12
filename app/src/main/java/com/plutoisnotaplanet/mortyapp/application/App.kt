package com.plutoisnotaplanet.mortyapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}