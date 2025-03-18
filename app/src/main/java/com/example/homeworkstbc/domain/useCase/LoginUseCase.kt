package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.domain.repository.LoginRepository
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginDomain>> {
        return loginRepository.login(email, password)
    }
}