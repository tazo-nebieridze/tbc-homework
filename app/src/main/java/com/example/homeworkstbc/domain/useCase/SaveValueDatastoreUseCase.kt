package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveValueUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun <T> invoke(key: PreferenceKeys, value: T) {
        dataStoreRepository.saveValue(key, value)
    }
}