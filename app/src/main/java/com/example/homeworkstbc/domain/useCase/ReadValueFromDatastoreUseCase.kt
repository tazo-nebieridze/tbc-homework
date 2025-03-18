package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadValueUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun <T> invoke(key: PreferenceKeys, default: T): T {
        return dataStoreRepository.readValue(key, default)
    }
}