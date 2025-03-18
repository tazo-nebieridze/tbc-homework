package com.example.homeworkstbc.domain.repository

import com.example.homeworkstbc.domain.utils.PreferenceKeys

interface DataStoreRepository {
    suspend fun <T> saveValue(key: PreferenceKeys, value: T)
    suspend fun <T> readValue(key: PreferenceKeys, default: T): T
    suspend fun deleteValue(key: PreferenceKeys)
}