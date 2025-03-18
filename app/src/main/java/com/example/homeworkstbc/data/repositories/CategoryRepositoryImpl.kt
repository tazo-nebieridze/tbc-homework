package com.example.homeworkstbc.data.repositories

import com.example.homeworkstbc.data.mappers.toDomain
import com.example.homeworkstbc.data.remote.api.CategoryService
import com.example.homeworkstbc.data.utils.ApiHelper
import com.example.homeworkstbc.domain.models.CategoryDomain
import com.example.homeworkstbc.domain.repository.CategoryRepository
import com.example.homeworkstbc.domain.utils.Resource
import com.example.homeworkstbc.domain.utils.mapResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val categoryService: CategoryService
) : CategoryRepository {

    override suspend fun fetchCategory(): Flow<Resource<List<CategoryDomain>>> {
        return apiHelper.handleHttpRequest {
            categoryService.fetchCategories()
        }.mapResource { list ->
            list.map { it.toDomain(parentCount = 0) }
        }
    }
}