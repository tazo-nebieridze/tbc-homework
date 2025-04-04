package com.example.firstcomposeproject.domain.useCase

import com.example.firstcomposeproject.domain.utils.PreferenceKeys

import com.example.firstcomposeproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String, rememberMe: Boolean) {
        val validityTime = if (rememberMe) {
            System.currentTimeMillis() + 5 * 60 * 1000
        } else {
            0L
        }
        dataStoreRepository.saveValue(PreferenceKeys.TOKEN, token)
        dataStoreRepository.saveValue(PreferenceKeys.TOKEN_VALIDITY_TIME, validityTime)
    }
}