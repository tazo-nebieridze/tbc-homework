package com.example.app

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.homeworkstbc.MyApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property on Context to get the DataStore
val Context.dataStore by preferencesDataStore(name = "MyAppPrefs")

object DataStoreManager {

    val PASSCODE = stringPreferencesKey("passcode")



    suspend fun savePasscode(passcode: String) {
        MyApplication.context?.dataStore?.edit { preferences ->
            preferences[PASSCODE] = passcode
        }
    }

    fun getPasscode(): Flow<String?> {
        return MyApplication.context?.dataStore?.data?.map { preferences ->
            preferences[PASSCODE]
        } ?: throw IllegalStateException("Context is null")
    }
}
