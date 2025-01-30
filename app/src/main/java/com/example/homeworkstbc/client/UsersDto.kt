package com.example.homeworkstbc.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersDto(
    val status: Boolean,
    @SerialName("additional_data") val additionalData: String? = null,
    val options: String? = null,
    val permissions: List<String?>?,
    val users: List<UserDto>
)

@Serializable
data class UserDto(
    val id: Int,
    val avatar: String?,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val about: String?,
    @SerialName("activation_status") val activationStatus: Double
)
