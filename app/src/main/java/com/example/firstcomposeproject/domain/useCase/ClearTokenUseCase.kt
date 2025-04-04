package com.example.firstcomposeproject.domain.useCase

import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import com.example.firstcomposeproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class ClearValueUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(key: PreferenceKeys) {
        dataStoreRepository.deleteValue(key)
    }
}