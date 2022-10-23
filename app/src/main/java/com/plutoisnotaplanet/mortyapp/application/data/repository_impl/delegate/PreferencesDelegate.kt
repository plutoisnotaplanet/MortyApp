package com.plutoisnotaplanet.mortyapp.application.data.repository_impl.delegate

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class PreferencesDelegate<TValue>(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defValue: TValue
) : ReadWriteProperty<Any?, TValue> {


    companion object {
        private val gson = GsonBuilder().create()
    }

    override fun getValue(
        thisRef: Any?, property:
        KProperty<*>
    ): TValue {

        with(preferences) {
            return when (defValue) {
                is Boolean -> (getBoolean(name, defValue) as? TValue) ?: defValue
                is Int -> (getInt(name, defValue) as TValue) ?: defValue
                is Float -> (getFloat(name, defValue) as TValue) ?: defValue
                is Long -> (getLong(name, defValue) as TValue) ?: defValue
                is String -> (getString(name, defValue) as TValue) ?: defValue
                is Date -> {
                    val time = getLong(name,0L)
                    val date = if(time == 0L) {
                        defValue
                    } else {
                        Date(time)
                    }
                    date as TValue
                }
                is List<*> -> gson.fromJson(getString(name, "[]"), defValue.javaClass) as TValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }
    }

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: TValue
    ) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(name, value)
                is Int -> putInt(name, value)
                is Float -> putFloat(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is List<*> -> putString(name, gson.toJson(value))
                is Date -> putLong(name, value.time)
                else -> throw NotFoundRealizationException(value)
            }
            apply()
        }
    }


    class NotFoundRealizationException(defValue: Any?) :
        Exception("Not found realization for $defValue")
}


inline fun <reified T> sharedPreferences(prefs: SharedPreferences, name: String, defValue: T) =
    PreferencesDelegate(prefs, name, defValue)