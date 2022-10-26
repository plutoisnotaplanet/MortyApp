package com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences

import android.content.Context
import android.content.SharedPreferences
import com.plutoisnotaplanet.mortyapp.application.Constants
import javax.inject.Inject

class MortyPreferences @Inject constructor(
    context: Context
) {

    companion object {
        private const val EMAIL = "login"
        private const val PASSWORD = "password"

        private const val IS_LOGGED = "is_logged"
        private const val IS_FILTERS_LOCAL = "is_filters_local"
        private const val SHOULD_USE_GRID_FOR_CHARACTERS = "should_use_grid_for_characters"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(Constants.APP_ID, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = preferences.edit()

    var email: String by sharedPreferences(preferences, EMAIL, "")
    var password: String by sharedPreferences(preferences, PASSWORD, "")
    var isLogged: Boolean by sharedPreferences(preferences, IS_LOGGED, false)
    var isFiltersLocal: Boolean by sharedPreferences(preferences, IS_FILTERS_LOCAL, false)
    var shouldUseGridForCharacters: Boolean by sharedPreferences(preferences, SHOULD_USE_GRID_FOR_CHARACTERS, false)

    fun logout() {
        editor.apply {
            remove(IS_LOGGED)
            remove(IS_FILTERS_LOCAL)
            remove(SHOULD_USE_GRID_FOR_CHARACTERS)
        }
        editor.commit()
    }
}