package com.example.firstcomposeproject.presentation.mappers

import com.example.firstcomposeproject.domain.models.RegisterDomain
import com.example.firstcomposeproject.presentation.models.Register


fun RegisterDomain.toRegister() : Register {
    return Register (
        token = this.token,
        id = this.id
    )
}