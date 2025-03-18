package com.example.homeworkstbc.presentation.home

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
}