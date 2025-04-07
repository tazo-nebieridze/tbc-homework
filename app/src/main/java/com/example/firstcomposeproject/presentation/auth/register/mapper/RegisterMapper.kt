package com.example.firstcomposeproject.presentation.auth.register.mapper

import com.example.firstcomposeproject.domain.models.RegisterDomain
import com.example.firstcomposeproject.presentation.auth.register.module.Register


fun RegisterDomain.toRegister() : Register {
    return Register (
        token = this.token,
        id = this.id
    )
}