package com.example.firstcomposeproject.presentation.profile

sealed class ProfileSideEffect {
    data object NavigateToLogin : ProfileSideEffect()
}