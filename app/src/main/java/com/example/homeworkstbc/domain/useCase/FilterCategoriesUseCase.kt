package com.example.homeworkstbc.domain.useCase

import com.example.homeworkstbc.domain.models.CategoryDomain
import javax.inject.Inject

class FilterCategoriesUseCase @Inject constructor() {
    operator fun invoke(categories: List<CategoryDomain>, query: String): List<CategoryDomain> {
        if (query.isBlank()) return categories
        return categories.flatMap { flatten(it) }
            .filter { it.name.contains(query, ignoreCase = true) }
    }

    private fun flatten(category: CategoryDomain): List<CategoryDomain> {
        return listOf(category) + category.children.flatMap { flatten(it) }
    }
}