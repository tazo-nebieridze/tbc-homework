package com.example.firstcomposeproject.presentation.mappers

import com.example.firstcomposeproject.domain.models.LoginDomain
import com.example.firstcomposeproject.presentation.models.Login

fun LoginDomain.toLogin() : Login {
    return Login(
        token = this.token
    )
}