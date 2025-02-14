// HomeViewModel.kt
package com.example.homeworkstbc.viewModels

import User
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.homeworkstbc.NetworkUtil
import com.example.homeworkstbc.client.UserService
import com.example.homeworkstbc.roomDatabase.AppDatabase
import com.example.homeworkstbc.roomDatabase.UserDb
import com.example.homeworkstbc.roomDatabase.UserRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: AppDatabase,
    private val userService: UserService,
    private val networkUtil: NetworkUtil
) : ViewModel() {

    private val isNetworkAvailable: () -> Boolean = networkUtil::isNetworkAvailable

    @OptIn(ExperimentalPagingApi::class)
    val usersFlow: Flow<androidx.paging.PagingData<User>> =
        Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            remoteMediator = UserRemoteMediator(
                database = database,
                service = userService,
                isNetworkAvailable = isNetworkAvailable
            ),
            pagingSourceFactory = { database.userDao().getPagingSource() }
        ).flow
            .map { pagingData ->
                pagingData.map { userDb -> userDb.toUser() }
            }
            .cachedIn(viewModelScope)

    fun UserDb.toUser(): User {
        return User(
            id = this.id,
            email = this.email ?: "",
            firstName = this.firstName ?: "",
            lastName = this.lastName ?: "",
            avatar = this.avatar ?: ""
        )
    }
}
