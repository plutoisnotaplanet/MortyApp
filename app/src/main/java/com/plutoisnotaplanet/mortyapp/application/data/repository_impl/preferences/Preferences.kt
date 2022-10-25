package com.plutoisnotaplanet.mortyapp.application.data.repository_impl.preferences

import android.content.Context
import android.content.SharedPreferences
import com.plutoisnotaplanet.mortyapp.application.Constants
import javax.inject.Inject

class Preferences @Inject constructor(
    context: Context
) {

    companion object {
        private const val USER_ID = "user_id"
        private const val LOGIN = "login"
        private const val PASSWORD = "password"

        private const val IS_LOGGED = "is_logged"

    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(Constants.APP_ID, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = preferences.edit()

    var userId: Long by sharedPreferences(preferences, USER_ID, 0)
    var login: String by sharedPreferences(preferences, LOGIN, "")
    var password: String by sharedPreferences(preferences, PASSWORD, "")
    var isLogged: Boolean by sharedPreferences(preferences, IS_LOGGED, false)

    fun logout() {
        editor.remove(IS_LOGGED)
        editor.commit()
    }
}