package com.example.firstcomposeproject.presentation.auth.logIn.mapper

import com.example.firstcomposeproject.domain.models.LoginDomain
import com.example.firstcomposeproject.presentation.auth.logIn.module.Login

fun LoginDomain.toLogin() : Login {
    return Login(
        token = this.token
    )
}