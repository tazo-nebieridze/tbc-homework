// DataStoreManager.kt
package com.example.app

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "MyAppPrefs")

object DataStoreManager {
    val JWT_TOKEN = stringPreferencesKey("jwt_token")
    val JWT_EXPIRATION = longPreferencesKey("jwt_expiration")
    val EMAIL = stringPreferencesKey("email")

    suspend fun saveToken(context: Context, token: String, email: String, expirationTime: Long) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
            preferences[JWT_EXPIRATION] = expirationTime
            preferences[EMAIL] = email
        }
    }

    fun getToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_TOKEN]
        }
    }

    fun getExpirationTime(context: Context): Flow<Long?> {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_EXPIRATION]
        }
    }

    fun getEmail(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL]
        }
    }

    suspend fun clearData(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
