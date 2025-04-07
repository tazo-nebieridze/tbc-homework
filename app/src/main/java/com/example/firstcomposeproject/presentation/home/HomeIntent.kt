package com.example.firstcomposeproject.presentation.home

sealed class HomeIntent {
    data object LoadUsers : HomeIntent()
}
