package com.example.homeworkstbc.data.api.dtos

import kotlinx.serialization.Serializable

@Serializable
data class StoreDto(
    val id: Int,
    val cover: String,
    val title: String
)
