package com.plutoisnotaplanet.mortyapp.application.data.database

import android.content.Context
import androidx.room.Room
import com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences.MortyPreferences
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbFindHelper
@Inject constructor(
    private val context: Context,
    private val preferences: MortyPreferences
){

    companion object {
        private const val APP_NAME = "user"
        private const val DB_EXTENSION = ".db"
    }

    private val databaseMap: MutableMap<String, MortyDataBase> = mutableMapOf()

    @Synchronized
    fun getDatabase(ownerId: String? = null): MortyDataBase {
        val database: MortyDataBase
        var profileId = ownerId
        if (profileId == null) {
            // if preferencesRepository.getProfile() is null after UnauthorizedException and logout, we create database with name Empty
            profileId = preferences.email
        }
        if (databaseMap.containsKey(profileId)) {
            database = databaseMap[profileId]!!
        } else {
            database = Room.databaseBuilder(context, MortyDataBase::class.java, getDatabaseName(profileId))
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
            databaseMap[profileId] = database
        }
        return database
    }

    private fun getDatabaseName(ownerId: String): String {
        return ownerId.lowercase(Locale.ROOT) + "_" + APP_NAME + DB_EXTENSION
    }
}