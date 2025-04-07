package com.example.firstcomposeproject.presentation.profile

data class ProfileState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)