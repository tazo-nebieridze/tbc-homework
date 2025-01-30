package com.example.homeworkstbc.viewModels

import User
import UserPagingSource
import UserPrefsRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.yourapp.datastore.UserPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = UserPrefsRepository()

    val userPrefsFlow: Flow<UserPrefs> = repository.userPrefsFlow.map { it }

    fun saveUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            repository.saveUserDetails(firstName, lastName, email)
        }
    }
}


