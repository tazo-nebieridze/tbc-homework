package com.example.firstcomposeproject.presentation.auth.register

data class RegisterState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordValid: Boolean = false,
    val repeatPassword: String = "",
    val isRepeatPasswordValid: Boolean = false,
    val isLoading: Boolean = false
)