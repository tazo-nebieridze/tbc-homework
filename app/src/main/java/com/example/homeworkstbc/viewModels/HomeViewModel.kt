package com.example.homeworkstbc.viewModels

import User
import UserPagingSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    val usersFlow: Flow<PagingData<User>> = Pager(
        config = PagingConfig(
            pageSize = 6,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { UserPagingSource() }
    ).flow.cachedIn(viewModelScope)

}



