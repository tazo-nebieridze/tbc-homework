package com.example.homeworkstbc.domain.repository

import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
   suspend fun login (email: String, password: String ) : Flow<Resource<LoginDomain>>
}