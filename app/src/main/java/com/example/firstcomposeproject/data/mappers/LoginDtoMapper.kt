package com.example.firstcomposeproject.data.mappers

import com.example.firstcomposeproject.data.remote.dto.LoginDto
import com.example.firstcomposeproject.domain.models.LoginDomain

fun LoginDto.toLoginDomain() : LoginDomain {
    return LoginDomain(
        token = this.token
    )
}