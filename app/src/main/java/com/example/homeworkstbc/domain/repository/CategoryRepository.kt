package com.example.homeworkstbc.domain.repository

import com.example.homeworkstbc.domain.models.CategoryDomain
import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun fetchCategory () : Flow<Resource<List<CategoryDomain>>>
}