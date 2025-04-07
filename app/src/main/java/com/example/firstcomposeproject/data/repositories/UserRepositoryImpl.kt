package com.example.firstcomposeproject.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.firstcomposeproject.data.mappers.toDomain
import com.example.firstcomposeproject.data.remote.api.UserService
import com.example.firstcomposeproject.data.utils.ApiHelperPaging
import com.example.firstcomposeproject.domain.models.User
import com.example.firstcomposeproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val apiHelperPaging: ApiHelperPaging
) : UserRepository {
    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 6, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(userService, apiHelperPaging) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}