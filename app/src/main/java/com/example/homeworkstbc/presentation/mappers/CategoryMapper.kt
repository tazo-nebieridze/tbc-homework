package com.example.homeworkstbc.presentation.mappers

import com.example.homeworkstbc.data.remote.dto.CategoryDto
import com.example.homeworkstbc.domain.models.CategoryDomain
import com.example.homeworkstbc.presentation.models.Category

fun CategoryDomain.toPresentation(): Category {
    return Category(
        id = this.id,
        name = this.name,
        nameDe = this.nameDe,
        createdAt = this.createdAt,
        bglNumber = this.bglNumber,
        bglVariant = this.bglVariant,
        orderId = this.orderId,
        main = this.main,
        children = this.children.map { it.toPresentation() },
        parentCount = this.parentCount
    )
}
 fun Category.toDomain(): CategoryDomain {
    return CategoryDomain(
        id = this.id,
        name = this.name,
        nameDe = this.nameDe,
        createdAt = this.createdAt,
        bglNumber = this.bglNumber,
        bglVariant = this.bglVariant,
        orderId = this.orderId,
        main = this.main,
        children = this.children.map { it.toDomain() },
        parentCount = this.parentCount
    )
}