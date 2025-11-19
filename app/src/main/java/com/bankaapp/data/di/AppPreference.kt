package com.bankaapp.data.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bankaapp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

const val preferenceName = BuildConfig.APPLICATION_ID

private val Context.dataStore by preferencesDataStore(name = preferenceName)

class AppPreference(private val cont: Context) {

    private object Keys {
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password_hash")
//        val IS_LOGGED_IN = stringPreferencesKey("is_logged_in")
    }

    // Save user credentials
    suspend fun saveUserCredentials(email: String, hashedPassword: String) {
        cont.dataStore.edit { preferences ->
            preferences[Keys.USER_EMAIL] = email
            preferences[Keys.USER_PASSWORD] = hashedPassword
//            preferences[Keys.IS_LOGGED_IN] = "true"
        }
    }

    // Get user email
    suspend fun getUserEmail(): String? {
        return cont.dataStore.data.map { it[Keys.USER_EMAIL] }.first()
    }

    //check if user already exist
    suspend fun isUserExists(email: String): Boolean {
        val storedEmail = cont.dataStore.data.map { it[Keys.USER_EMAIL] }.first()
        return storedEmail == email
    }


    // Get user password hash
    suspend fun getUserPasswordHash(): String? {
        return cont.dataStore.data.map { it[Keys.USER_PASSWORD] }.first()
    }


    // Verify login credentials
    suspend fun verifyCredentials(email: String, hashedPassword: String): Boolean {
        val prefs = cont.dataStore.data.first()
        val storedEmail = prefs[Keys.USER_EMAIL]
        val storedPassword = prefs[Keys.USER_PASSWORD]
        return storedEmail == email && storedPassword == hashedPassword
    }



}