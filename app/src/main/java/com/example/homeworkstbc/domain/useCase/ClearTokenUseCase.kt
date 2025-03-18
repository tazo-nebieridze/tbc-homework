package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearValueUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(key: PreferenceKeys) {
        dataStoreRepository.deleteValue(key)
    }
}