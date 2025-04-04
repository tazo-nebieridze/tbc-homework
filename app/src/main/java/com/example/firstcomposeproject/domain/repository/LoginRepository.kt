package com.example.firstcomposeproject.domain.repository

import com.example.firstcomposeproject.domain.models.LoginDomain
import com.example.firstcomposeproject.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
   suspend fun login (email: String, password: String ) : Flow<Resource<LoginDomain>>
}