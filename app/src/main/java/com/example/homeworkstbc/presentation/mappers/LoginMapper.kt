package com.example.homeworkstbc.presentation.mappers

import com.example.homeworkstbc.domain.models.LoginDomain
import com.example.homeworkstbc.presentation.models.Login

fun LoginDomain.toLogin() : Login{
    return Login(
        token = this.token
    )
}