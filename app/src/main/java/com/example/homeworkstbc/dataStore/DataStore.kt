// DataStoreManager.kt
package com.example.app

import UserPrefsSerializer
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.homeworkstbc.MyApplication
import com.example.yourapp.datastore.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "MyAppPrefs")






object DataStoreManager {

    val JWT_TOKEN = stringPreferencesKey("jwt_token")
    val JWT_EXPIRATION = longPreferencesKey("jwt_expiration")
    val EMAIL = stringPreferencesKey("email")

    suspend fun saveToken( token: String, email: String, expirationTime: Long) {
        
        MyApplication.context?.dataStore?.edit { preferences ->
            preferences[JWT_TOKEN] = token
            preferences[JWT_EXPIRATION] = expirationTime
            preferences[EMAIL] = email
        }
    }

    fun getToken(): Flow<String?> {
        return MyApplication.context?.dataStore?.data!!.map { preferences ->
            preferences[JWT_TOKEN]
        }
    }

    fun getExpirationTime(): Flow<Long?> {
        return MyApplication.context?.dataStore?.data!!.map { preferences ->
            preferences[JWT_EXPIRATION]
        }
    }

    fun getEmail(): Flow<String?> {
        return MyApplication.context?.dataStore?.data!!.map { preferences ->
            preferences[EMAIL]
        }
    }

    suspend fun clearData() {
        MyApplication.context?.dataStore?.edit { preferences ->
            preferences.clear()
        }
    }
}
