package com.example.homeworkstbc.data.mappers

import com.example.homeworkstbc.data.remote.dto.LoginDto
import com.example.homeworkstbc.domain.models.LoginDomain

fun LoginDto.toLoginDomain() : LoginDomain {
    return LoginDomain(
        token = this.token
    )
}