package com.example.firstcomposeproject.data.mappers

import com.example.firstcomposeproject.data.remote.dto.RegisterDto
import com.example.firstcomposeproject.domain.models.RegisterDomain


fun RegisterDto.toRegisterDomain() : RegisterDomain {
    return RegisterDomain(
        id = this.id,
        token = this.token
    )
}