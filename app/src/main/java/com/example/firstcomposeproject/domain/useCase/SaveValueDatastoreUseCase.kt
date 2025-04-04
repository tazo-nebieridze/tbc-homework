package com.example.firstcomposeproject.domain.useCase

import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import com.example.firstcomposeproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveValueUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun <T> invoke(key: PreferenceKeys, value: T) {
        dataStoreRepository.saveValue(key, value)
    }
}