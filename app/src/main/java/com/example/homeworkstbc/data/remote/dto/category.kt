package com.example.homeworkstbc.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    @SerialName("name_de") val nameDe: String,
    val createdAt: String,
    @SerialName("bgl_number") val bglNumber: String? = null,
    @SerialName("bgl_variant") val bglVariant: String? = null,
    @SerialName("order_id") val orderId: Int? = null,
    val main: String? = null,
    val children: List<CategoryDto> = emptyList()
)