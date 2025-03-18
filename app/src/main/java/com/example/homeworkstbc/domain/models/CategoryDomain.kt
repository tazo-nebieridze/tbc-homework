package com.example.homeworkstbc.domain.models



data class CategoryDomain (
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String? = null,
    val bglVariant: String? = null,
    val orderId: Int? = null,
    val main: String? = null,
    val children: List<CategoryDomain> = emptyList(),
    val parentCount: Int = 0
    )