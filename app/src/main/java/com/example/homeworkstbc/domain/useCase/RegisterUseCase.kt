package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.models.RegisterDomain
import com.example.homeworkstbc.domain.repository.RegisterRepository
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterDomain>> {
        return registerRepository.register(email, password)
    }
}