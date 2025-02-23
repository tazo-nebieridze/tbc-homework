package com.example.homeworkstbc.client

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val email: String,
    val password: String
)

@Serializable
data class RegisterDto(
    val id: Int? = null,
    val token: String? = null
)