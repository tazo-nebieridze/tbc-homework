// DataStoreManager.kt
package com.example.app

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a DataStore instance
val Context.dataStore by preferencesDataStore(name = "MyAppPrefs")

object DataStoreManager {
    val JWT_TOKEN = stringPreferencesKey("jwt_token")
    val JWT_EXPIRATION = longPreferencesKey("jwt_expiration")
    val EMAIL = stringPreferencesKey("email")

    // Save token and data
    suspend fun saveToken(context: Context, token: String, email: String, expirationTime: Long) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
            preferences[JWT_EXPIRATION] = expirationTime
            preferences[EMAIL] = email
        }
    }

    // Get token
    fun getToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_TOKEN]
        }
    }

    // Get expiration time
    fun getExpirationTime(context: Context): Flow<Long?> {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_EXPIRATION]
        }
    }

    // Get email
    fun getEmail(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL]
        }
    }

    // Clear all data (logout)
    suspend fun clearData(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
