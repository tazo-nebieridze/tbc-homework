package com.example.homeworkstbc.domain.repository

import com.example.homeworkstbc.domain.models.RegisterDomain
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register (email: String, password: String ) : Flow<Resource<RegisterDomain>>
}