package com.example.firstcomposeproject.presentation.auth.logIn

sealed class LoginSideEffect {
    data class ShowError(val message: String) : LoginSideEffect()
    data object NavigateToHome : LoginSideEffect()
}