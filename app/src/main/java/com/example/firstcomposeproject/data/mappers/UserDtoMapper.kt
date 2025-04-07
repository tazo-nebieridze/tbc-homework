package com.example.firstcomposeproject.data.mappers

import com.example.firstcomposeproject.data.remote.dto.UserDto
import com.example.firstcomposeproject.domain.models.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}