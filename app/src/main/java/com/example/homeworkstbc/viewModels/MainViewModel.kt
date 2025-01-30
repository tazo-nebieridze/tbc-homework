package com.example.homeworkstbc.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RetrofitClient
import com.example.homeworkstbc.dto.UserDto
import com.example.homeworkstbc.roomDatabase.AppDatabase
import com.example.homeworkstbc.roomDatabase.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

class MainViewModel : ViewModel() {

    private val _mainUsersState = MutableStateFlow<MainUsersState>(MainUsersState.Idle)
    val mainUsersState: StateFlow<MainUsersState> get() = _mainUsersState

    fun fetchUsers() {
        if (_mainUsersState.value is MainUsersState.Success) return

        viewModelScope.launch(Dispatchers.IO) {
            _mainUsersState.value = MainUsersState.Loading
            try {
                val response = RetrofitClient.registerService.getUsers()
                if (response.isSuccessful) {
                    response.body()?.users?.let { userList ->
                        val db = AppDatabase.getInstance()
                        val roomUsers = userList.map { dto ->
                            User(
                                uid = dto.id,
                                avatar = dto.avatar,
                                firstName = dto.firstName,
                                lastName = dto.lastName,
                                about = dto.about,
                                activationStatus = dto.activationStatus
                            )
                        }
                        db.userDao().insertAll(*roomUsers.toTypedArray())

                        _mainUsersState.value = MainUsersState.Success(userList, isOffline = false)
                    } ?: run {
                        _mainUsersState.value = MainUsersState.Error("Response body is null")
                    }
                } else {
                    _mainUsersState.value = MainUsersState.Error("Failed to fetch users: ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network error: ${e.message}")
                val db = AppDatabase.getInstance()
                val localUsers = db.userDao().getAll().first()
                if (localUsers.isNotEmpty()) {
                    val userDtos = localUsers.map { roomUser ->
                        UserDto(
                            id = roomUser.uid,
                            avatar = roomUser.avatar,
                            firstName = roomUser.firstName ?: "",
                            lastName = roomUser.lastName ?: "",
                            about = roomUser.about,
                            activationStatus = roomUser.activationStatus ?: 0.0
                        )
                    }
                    _mainUsersState.value = MainUsersState.Success(userDtos, isOffline = true)
                } else {
                    _mainUsersState.value = MainUsersState.Error("Offline and no cached data available")
                }
            } catch (e: Exception) {
                _mainUsersState.value = MainUsersState.Error("An unexpected error occurred: ${e.message}")
            }
        }
    }
}

sealed class MainUsersState {
    object Idle : MainUsersState()
    object Loading : MainUsersState()
    data class Success(val users: List<UserDto>, val isOffline: Boolean = false) : MainUsersState()
    data class Error(val message: String) : MainUsersState()
}










//    val db = AppDatabase.getInstance()
//
//
//    val userDao = db.userDao()
//
//    private val _users = MutableStateFlow<List<User>>(emptyList())
//    val users: StateFlow<List<User>> get() = _users
//
//    fun addUser(id: Int, firstName: String, lastName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            userDao.insertAll(User(id, firstName, lastName))
//            Log.d("MainViewModel", "User inserted: $firstName $lastName")
//        }
//    }
//
//
//
//     fun observeUsers() {
//        viewModelScope.launch {
//            userDao.getAll().collect { userList ->
//                _users.value = userList
//                Log.d("MainViewModel", "Loaded users: $userList")
//            }
//        }
//    }


//    private val repository = UserPrefsRepository
//    val userPrefsFlow: Flow<UserPrefs> = repository.userPrefsFlow
//
//    fun saveUser(firstName: String, lastName: String, email: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.saveUserDetails(firstName, lastName, email)
//        }
//    }



