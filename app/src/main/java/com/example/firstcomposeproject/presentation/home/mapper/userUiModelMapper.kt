package com.example.firstcomposeproject.presentation.home.mapper

import com.example.firstcomposeproject.domain.models.User
import com.example.firstcomposeproject.presentation.home.module.UserUiModel

fun User.toUiModel(): UserUiModel {
    return UserUiModel(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        avatar = avatar
    )
}