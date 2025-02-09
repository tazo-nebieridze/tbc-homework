package com.example.homeworkstbc.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

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



