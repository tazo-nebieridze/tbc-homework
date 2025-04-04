package com.example.firstcomposeproject.data.repositories

import com.example.firstcomposeproject.data.remote.api.LoginService
import com.example.firstcomposeproject.data.remote.dto.LoginRequest

import com.example.firstcomposeproject.data.mappers.toLoginDomain
import com.example.firstcomposeproject.data.utils.ApiHelper
import com.example.firstcomposeproject.domain.models.LoginDomain
import com.example.firstcomposeproject.domain.repository.LoginRepository
import com.example.firstcomposeproject.domain.utils.Resource
import com.example.firstcomposeproject.domain.utils.mapResource
import kotlinx.coroutines.flow.Flow
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