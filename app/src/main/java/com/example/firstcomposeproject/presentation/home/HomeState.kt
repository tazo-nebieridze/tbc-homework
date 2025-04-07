package com.example.firstcomposeproject.presentation.home

import androidx.paging.PagingData
import com.example.firstcomposeproject.presentation.home.module.UserUiModel
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val usersFlow: Flow<PagingData<UserUiModel>>
)