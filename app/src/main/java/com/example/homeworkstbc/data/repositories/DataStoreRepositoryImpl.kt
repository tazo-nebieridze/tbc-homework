package com.example.homeworkstbc.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override suspend fun <T> saveValue(key: PreferenceKeys, value: T) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key.key)] = value
                is Long -> preferences[longPreferencesKey(key.key)] = value
            }
        }
    }

    override suspend fun <T> readValue(key: PreferenceKeys, default: T): T {
        return when (default) {
            is String -> dataStore.data.map { it[stringPreferencesKey(key.key)] ?: default }.first() as T
            is Long -> dataStore.data.map { it[longPreferencesKey(key.key)] ?: default }.first() as T
            else -> default
        }
    }

    override suspend fun deleteValue(key: PreferenceKeys) {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key.key))
        }
    }
}