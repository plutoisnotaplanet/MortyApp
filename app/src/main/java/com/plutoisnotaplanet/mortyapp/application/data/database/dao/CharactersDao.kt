package com.plutoisnotaplanet.mortyapp.application.data.database.dao

import androidx.room.*
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.CharacterEntity
import com.plutoisnotaplanet.mortyapp.application.data.database.entitites.UserProfileEntity
import com.plutoisnotaplanet.mortyapp.application.data.rest.response.CharacterDto
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(value: CharacterEntity)

    @Query("DELETE FROM characters_db WHERE id = :id")
    abstract suspend fun delete(id: String)

    @Query("DELETE FROM characters_db")
    abstract suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(value: CharacterEntity)

    @Query("SELECT EXISTS(SELECT * FROM characters_db WHERE id =:id)")
    abstract suspend fun hasCharacter(id: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<CharacterEntity>)

    @Query("SELECT * FROM characters_db WHERE id = :id_")
    abstract suspend fun getCharacterById(id_: Long): CharacterEntity

    @Query("SELECT * FROM characters_db WHERE page = :page_")
    abstract suspend fun getCharacterListByPage(page_: Int): List<CharacterEntity>

    @Query("SELECT * FROM characters_db")
    abstract suspend fun getAllCharacters(): List<CharacterEntity>

    @Transaction
    open suspend fun save(value: CharacterEntity) {
        val exist = hasCharacter(value.id)
        if (exist) {
            update(value)
        } else insert(value)
    }

    @Transaction
    open suspend fun save(list: List<CharacterEntity>) {
        if (list.isEmpty()) return

        list.forEach {
            save(it)
        }
    }

}