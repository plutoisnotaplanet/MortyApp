package com.plutoisnotaplanet.mortyapp.application.data.database.entitites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plutoisnotaplanet.mortyapp.application.data.database.MortyDataBase
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.LocalPhotoModel
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.UserProfile

@Entity(tableName = MortyDataBase.USER_PROFILE_DB)
data class UserProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "email")
    val email: String,
    val photoData: LocalPhotoModel? = LocalPhotoModel(),
    val favoriteCharactersList: MutableList<Character>? = mutableListOf()
) {

    fun toModel(): UserProfile {
        return UserProfile(
            email = email,
            photoData = photoData,
            favoriteCharactersList = favoriteCharactersList
        )
    }
}