package com.example.firstcomposeproject.domain.useCase

import androidx.paging.PagingData
import com.example.firstcomposeproject.domain.models.User
import com.example.firstcomposeproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<PagingData<User>> {
        return userRepository.getUsers()
    }
}