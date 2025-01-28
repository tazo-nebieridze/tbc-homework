package com.example.homeworkstbc.client

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginDto(
    val token: String? = null
)