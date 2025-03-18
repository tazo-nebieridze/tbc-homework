package com.example.homeworkstbc.data.repositories

import com.example.homeworkstbc.data.remote.api.LoginService
import com.example.homeworkstbc.data.remote.dto.LoginRequest

import com.example.homeworkstbc.data.mappers.toLoginDomain
import com.example.homeworkstbc.data.utils.ApiHelper
import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.domain.repository.LoginRepository
import com.example.homeworkstbc.domain.utils.Resource
import com.example.homeworkstbc.domain.utils.mapResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val loginService: LoginService
) : LoginRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<LoginDomain>> {
        return apiHelper.handleHttpRequest {
            loginService.login(LoginRequest(email, password))
        }.mapResource {
            it.toLoginDomain()
        }
    }
}