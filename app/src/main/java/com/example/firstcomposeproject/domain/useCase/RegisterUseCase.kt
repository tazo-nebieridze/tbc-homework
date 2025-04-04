package com.example.firstcomposeproject.domain.useCase

import com.example.firstcomposeproject.domain.models.RegisterDomain
import com.example.firstcomposeproject.domain.repository.RegisterRepository
import com.example.firstcomposeproject.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<RegisterDomain>> {
        return registerRepository.register(email, password)
    }
}