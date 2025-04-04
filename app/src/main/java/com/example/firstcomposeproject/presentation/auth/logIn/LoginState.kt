package com.example.firstcomposeproject.presentation.auth.logIn

data class LoginState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordValid: Boolean = false,
    val isLoading: Boolean = false,
    val rememberMe: Boolean = false
)