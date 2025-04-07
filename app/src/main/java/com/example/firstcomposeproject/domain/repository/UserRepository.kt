package com.example.firstcomposeproject.domain.repository

import androidx.paging.PagingData
import com.example.firstcomposeproject.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<PagingData<User>>
}