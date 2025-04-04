package com.example.firstcomposeproject.presentation.auth.register

sealed class RegisterIntent {
    data class EmailChanged(val email: String) : RegisterIntent()
    data class PasswordChanged(val password: String) : RegisterIntent()
    data class RepeatPasswordChanged(val repeatPassword: String) : RegisterIntent()
    data object RegisterClicked : RegisterIntent()
}