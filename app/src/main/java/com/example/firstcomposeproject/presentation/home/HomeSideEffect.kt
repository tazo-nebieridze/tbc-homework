package com.example.firstcomposeproject.presentation.home

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}