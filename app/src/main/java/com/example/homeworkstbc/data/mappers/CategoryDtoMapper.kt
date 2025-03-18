package com.example.homeworkstbc.data.mappers


import com.example.homeworkstbc.data.remote.dto.CategoryDto
import com.example.homeworkstbc.domain.models.CategoryDomain


fun CategoryDto.toDomain(parentCount: Int = 0) : CategoryDomain {
    return CategoryDomain(
        id = this.id,
        name = this.name,
        nameDe = this.nameDe,
        createdAt = this.createdAt,
        bglNumber = this.bglNumber,
        bglVariant = this.bglVariant,
        orderId = this.orderId,
        main = this.main,
        children = this.children.map { it.toDomain(parentCount + 1) },
        parentCount = parentCount
    )
}
