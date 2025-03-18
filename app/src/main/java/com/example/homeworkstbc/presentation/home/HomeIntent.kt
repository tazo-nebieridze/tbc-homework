package com.example.homeworkstbc.presentation.home

sealed class HomeIntent {
    data object FetchCategories : HomeIntent()
    data class SearchCategories(val query: String) : HomeIntent()
}