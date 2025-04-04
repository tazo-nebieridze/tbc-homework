package com.example.firstcomposeproject.domain.repository

import com.example.firstcomposeproject.domain.models.RegisterDomain
import com.example.firstcomposeproject.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register (email: String, password: String ) : Flow<Resource<RegisterDomain>>
}