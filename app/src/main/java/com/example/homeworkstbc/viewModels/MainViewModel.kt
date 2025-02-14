package com.example.homeworkstbc.viewModels

import Resource
import ItemsDto
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _itemsFlow = MutableStateFlow<Resource<List<ItemsDto>>>(Resource.Idle)
    val itemsFlow: StateFlow<Resource<List<ItemsDto>>> = _itemsFlow

    fun fetchItems() {
        viewModelScope.launch {
            _itemsFlow.value = Resource.Loading
            _itemsFlow.value = repository.fetchItems()
        }
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



