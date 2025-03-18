package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.models.CategoryDomain
import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.domain.repository.CategoryRepository
import com.example.homeworkstbc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<CategoryDomain>>> {
        return categoryRepository.fetchCategory()
    }
}