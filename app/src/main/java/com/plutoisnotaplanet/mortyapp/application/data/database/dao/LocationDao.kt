package com.plutoisnotaplanet.mortyapp.application.data.database.dao

import androidx.room.*
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.CharacterEntity
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.LocationEntity
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location

@Dao
abstract class LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(value: LocationEntity)

    @Query("DELETE FROM locations_db WHERE id = :id")
    abstract suspend fun delete(id: String)

    @Query("DELETE FROM locations_db")
    abstract suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(value: LocationEntity)

    @Query("SELECT EXISTS(SELECT * FROM locations_db WHERE id =:id)")
    abstract suspend fun hasCharacter(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<LocationEntity>)

    @Query("SELECT * FROM locations_db WHERE id = :id_")
    abstract suspend fun getLocationById(id_: Long): LocationEntity

    @Query("SELECT * FROM locations_db WHERE page = :page_")
    abstract suspend fun getLocationsListByPage(page_: Int): List<LocationEntity>

    @Transaction
    open suspend fun save(value: LocationEntity) {
        val exist = hasCharacter(value.id)
        if (exist) {
            update(value)
        } else insert(value)
    }

    @Transaction
    open suspend fun save(list: List<LocationEntity>) {
        if (list.isEmpty()) return

        list.forEach {
            save(it)
        }
    }

}