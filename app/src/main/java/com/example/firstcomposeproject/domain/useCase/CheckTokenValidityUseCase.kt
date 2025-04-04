package com.example.firstcomposeproject.domain.useCase


import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import com.example.firstcomposeproject.domain.repository.DataStoreRepository
import javax.inject.Inject

class CheckTokenValidityUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = dataStoreRepository.readValue(PreferenceKeys.TOKEN, "")
        val validityTime = dataStoreRepository.readValue(PreferenceKeys.TOKEN_VALIDITY_TIME, 0L)
        return token.isNotEmpty() && System.currentTimeMillis() < validityTime
    }
}