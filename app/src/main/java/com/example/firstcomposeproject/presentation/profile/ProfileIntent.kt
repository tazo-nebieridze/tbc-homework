package com.example.firstcomposeproject.presentation.profile

sealed class ProfileIntent {
    object LoadEmail : ProfileIntent()
    object Logout : ProfileIntent()
}
