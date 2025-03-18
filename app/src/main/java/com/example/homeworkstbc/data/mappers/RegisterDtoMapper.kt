package com.example.homeworkstbc.data.mappers

import com.example.homeworkstbc.data.remote.dto.RegisterDto
import com.example.homeworkstbc.domain.models.RegisterDomain


fun RegisterDto.toRegisterDomain() : RegisterDomain {
    return RegisterDomain(
        id = this.id,
        token = this.token
    )
}