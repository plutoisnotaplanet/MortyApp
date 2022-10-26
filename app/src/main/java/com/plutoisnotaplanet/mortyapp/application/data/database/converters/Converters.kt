package com.plutoisnotaplanet.mortyapp.application.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plutoisnotaplanet.mortyapp.application.domain.model.LocalPhotoModel
import com.plutoisnotaplanet.mortyapp.application.domain.model.Location
import com.plutoisnotaplanet.mortyapp.application.domain.model.Character

object Converters {

    @TypeConverter
    fun toCharactersList(value: String): List<Character> {
        val listType = object : TypeToken<List<Character>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCharactersList(list: List<Character>): String {
        val listType = object : TypeToken<List<Character>>() {}.type
        return Gson().toJson(list, listType)
    }

    @TypeConverter
    fun toCharacter(character: String): Character {
        return Gson().fromJson(character, Character::class.java)
    }

    @TypeConverter
    fun fromCharacter(character: Character): String {
        return Gson().toJson(character, Character::class.java)
    }

    @TypeConverter
    fun toLongList(list: String): List<Long> {
        val type = object : TypeToken<List<Long>>() {}.type
        return Gson().fromJson(list, type)
    }

    @TypeConverter
    fun fromLongList(list: List<Long>): String {
        val type = object : TypeToken<List<Long>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toLocation(location: String): Location {
        val type = object : TypeToken<Location>() {}.type
        return Gson().fromJson(location, type)
    }

    @TypeConverter
    fun fromLocation(location: Location): String {
        val type = object : TypeToken<Location>() {}.type
        return Gson().toJson(location, type)
    }

    @TypeConverter
    fun toStringList(list: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(list, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun toLocalPhotoModel(data: String): LocalPhotoModel {
        val type = object : TypeToken<LocalPhotoModel>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromLocalPhotoModel(data: LocalPhotoModel): String {
        val type = object : TypeToken<LocalPhotoModel>() {}.type
        return Gson().toJson(data, type)
    }

}