package com.example.homeworkstbc.domain.useCase


import com.example.homeworkstbc.domain.utils.PreferenceKeys
import com.example.homeworkstbc.domain.repository.DataStoreRepository
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