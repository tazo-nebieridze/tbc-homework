package com.example.homeworkstbc.presentation.home

import com.example.homeworkstbc.domain.models.CategoryDomain
import com.example.homeworkstbc.presentation.models.Category

data class HomeState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)