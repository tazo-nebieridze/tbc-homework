package com.example.homeworkstbc.presentation.auth.logIn

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    data class RememberMeToggled(val isChecked: Boolean) : LoginIntent()
    data object LoginClicked : LoginIntent()
}