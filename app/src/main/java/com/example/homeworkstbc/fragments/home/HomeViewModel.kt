package com.example.homeworkstbc.fragments.home

import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.homeworkstbc.roomDatabase.UserDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pager : Pager<Int, UserDb>
) : ViewModel() {

    val usersFlow: Flow<PagingData<User>> =
        pager
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainUser() }
            }
            .cachedIn(viewModelScope)
}
fun UserDb.toDomainUser(): User {
    return User(
        id = this.id,
        email = this.email ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        avatar = this.avatar ?: ""
    )
}