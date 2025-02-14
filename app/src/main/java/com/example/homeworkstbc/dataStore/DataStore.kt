package com.example.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "MyAppPrefs")

class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val JWT_TOKEN = stringPreferencesKey("jwt_token")
        val JWT_EXPIRATION = longPreferencesKey("jwt_expiration")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveToken(token: String, email: String, expirationTime: Long) {
        dataStore.edit { preferences ->
            preferences[JWT_TOKEN] = token
            preferences[JWT_EXPIRATION] = expirationTime
            preferences[EMAIL] = email
        }
    }

    fun getToken(): Flow<String?> =
        dataStore.data.map { preferences -> preferences[JWT_TOKEN] }

    fun getExpirationTime(): Flow<Long?> =
        dataStore.data.map { preferences -> preferences[JWT_EXPIRATION] }

    fun getEmail(): Flow<String?> =
        dataStore.data.map { preferences -> preferences[EMAIL] }

    suspend fun clearData() {
        dataStore.edit { it.clear() }
    }
}
