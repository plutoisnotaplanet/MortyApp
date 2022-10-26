package com.plutoisnotaplanet.mortyapp.application.data.database.dao

import androidx.room.*
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(value: UserProfileEntity)

    @Query("DELETE FROM user_profile_db WHERE email =:email")
    abstract suspend fun delete(email: String)

    @Query("DELETE FROM user_profile_db")
    abstract suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(value: UserProfileEntity)

    @Query("SELECT * FROM user_profile_db WHERE email = :email")
    abstract suspend fun getProfileByEmail(email: String): UserProfileEntity

    @Query("SELECT * FROM user_profile_db WHERE email = :email")
    abstract fun getProfileByEmailFlow(email: String): Flow<UserProfileEntity>

    @Query("SELECT EXISTS(SELECT * FROM user_profile_db WHERE email = :email)")
    abstract fun hasProfile(email: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<UserProfileEntity>)

    @Transaction
    open suspend fun save(value: UserProfileEntity) {
        if (hasProfile(value.email)) {
            update(value)
        } else {
            insert(value)
        }
    }
}