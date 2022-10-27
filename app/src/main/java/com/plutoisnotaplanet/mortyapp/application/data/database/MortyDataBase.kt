package com.plutoisnotaplanet.mortyapp.application.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plutoisnotaplanet.mortyapp.application.data.database.converters.Converters
import com.plutoisnotaplanet.mortyapp.application.data.database.dao.CharactersDao
import com.plutoisnotaplanet.mortyapp.application.data.database.dao.LocationDao
import com.plutoisnotaplanet.mortyapp.application.data.database.dao.UserProfileDao
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.CharacterEntity
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.LocationEntity
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.UserProfileEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class,
        UserProfileEntity::class
    ], version = 12
)
@TypeConverters(Converters::class)
abstract class MortyDataBase : RoomDatabase() {

    companion object {
        const val USER_PROFILE_DB = "user_profile_db"
        const val CHARACTERS_DB = "characters_db"
        const val LOCATIONS_DB = "locations_db"
        const val EPISODES_DB = "episodes_db"
    }

    abstract val userProfileDao: UserProfileDao
    abstract val charactersDao: CharactersDao
    abstract val locationsDao: LocationDao
}