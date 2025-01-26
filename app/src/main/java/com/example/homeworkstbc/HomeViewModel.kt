package com.example.homeworkstbc

import User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel : ViewModel() {

    private val _usersState = MutableStateFlow<UsersState>(UsersState.Loading)
    val usersState: StateFlow<UsersState> = _usersState

    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _usersState.value = UsersState.Loading

            try {
                val response = RetrofitClient.registerService.fetchUsers()
                if (response.isSuccessful) {
                    response.body()?.data?.let { userList ->
                        _usersState.value = UsersState.Success(userList)
                    }
                } else {
                    _usersState.value = UsersState.Error("Failed to fetch users: ${response.message()}")
                }
            } catch (e: HttpException) {
                _usersState.value = UsersState.Error("An unexpected error occurred: ${e.message}")
            } catch (e: IOException) {
                _usersState.value = UsersState.Error("Could not reach the server. Check your internet connection.")
            }
        }
    }
}

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<User>) : UsersState()
    data class Error(val message: String) : UsersState()
}
