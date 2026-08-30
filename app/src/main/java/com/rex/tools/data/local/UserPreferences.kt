package com.rex.tools.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PASSWORD = stringPreferencesKey("password")
    }

    suspend fun saveUser(email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_EMAIL] = email
            prefs[KEY_PASSWORD] = password
        }
    }

    fun getUser(): Flow<Pair<String, String>> {
        return context.dataStore.data.map { prefs ->
            Pair(prefs[KEY_EMAIL] ?: "", prefs[KEY_PASSWORD] ?: "")
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}
