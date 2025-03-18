package com.example.homeworkstbc.presentation.mappers

import com.example.homeworkstbc.domain.models.RegisterDomain
import com.example.homeworkstbc.presentation.models.Register


fun RegisterDomain.toRegister() : Register {
    return Register (
        token = this.token,
        id = this.id
    )
}