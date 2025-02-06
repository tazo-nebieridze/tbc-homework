package com.example.homeworkstbc.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    val lockScreenComponents: List<String> = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "f", "0", "d"
    )

    private val _currentInput = MutableStateFlow("")
    val currentInput: StateFlow<String> = _currentInput

    fun onKeyPressed(key: String) {
        when (key) {
            "d" -> {
                if (_currentInput.value.isNotEmpty()) {
                    _currentInput.value = _currentInput.value.dropLast(1)
                }
            }
            "f" -> {

            }
            else -> {
                if (_currentInput.value.length < 4) {
                    _currentInput.value += key
                }
            }
        }
    }


    fun verifyPasscode(correctPasscode: String): Boolean {
        return _currentInput.value == correctPasscode
    }

    fun clearInput() {
        _currentInput.value = ""
    }
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



