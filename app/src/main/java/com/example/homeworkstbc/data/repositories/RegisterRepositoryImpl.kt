package com.example.homeworkstbc.data.repositories

import com.example.homeworkstbc.data.remote.api.RegisterService
import com.example.homeworkstbc.data.remote.dto.RegisterRequest
import com.example.homeworkstbc.data.mappers.toRegisterDomain
import com.example.homeworkstbc.data.utils.ApiHelper
import com.example.homeworkstbc.domain.models.RegisterDomain
import com.example.homeworkstbc.domain.repository.RegisterRepository
import com.example.homeworkstbc.domain.utils.Resource
import com.example.homeworkstbc.domain.utils.mapResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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