package com.example.homeworkstbc.presentation.utils

import com.example.homeworkstbc.presentation.models.Category

fun List<Category>.flatten(): List<Category> {
    return this.flatMap { listOf(it) + it.children.flatten() }
}