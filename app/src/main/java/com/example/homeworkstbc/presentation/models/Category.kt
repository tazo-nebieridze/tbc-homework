package com.example.homeworkstbc.presentation.models

data class Category(
    val id: String,
    val name: String,
    val nameDe: String,
    val createdAt: String,
    val bglNumber: String? = null,
    val bglVariant: String? = null,
    val orderId: Int? = null,
    val main: String? = null,
    val children: List<Category> = emptyList(),
    val parentCount: Int = 0
)
