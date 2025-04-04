package com.example.firstcomposeproject.domain.useCase

import com.example.firstcomposeproject.domain.models.LoginDomain
import com.example.firstcomposeproject.domain.repository.LoginRepository
import com.example.firstcomposeproject.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginDomain>> {
        return loginRepository.login(email, password)
    }
}