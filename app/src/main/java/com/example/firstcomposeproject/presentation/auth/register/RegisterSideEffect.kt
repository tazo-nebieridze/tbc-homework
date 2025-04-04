package com.example.firstcomposeproject.presentation.auth.register

sealed class RegisterSideEffect {
    data class ShowError(val message: String) : RegisterSideEffect()
    data object NavigateToLogin : RegisterSideEffect()
}