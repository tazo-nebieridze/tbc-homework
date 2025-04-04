package com.example.firstcomposeproject.data.repositories

import com.example.firstcomposeproject.data.remote.api.RegisterService
import com.example.firstcomposeproject.data.remote.dto.RegisterRequest
import com.example.firstcomposeproject.data.mappers.toRegisterDomain
import com.example.firstcomposeproject.data.utils.ApiHelper
import com.example.firstcomposeproject.domain.models.RegisterDomain
import com.example.firstcomposeproject.domain.repository.RegisterRepository
import com.example.firstcomposeproject.domain.utils.Resource
import com.example.firstcomposeproject.domain.utils.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val registerService: RegisterService
) : RegisterRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<RegisterDomain>> {
        return apiHelper.handleHttpRequest {
            registerService.register(RegisterRequest(email,password))
        }.mapResource {
            it.toRegisterDomain()
        }
    }
}