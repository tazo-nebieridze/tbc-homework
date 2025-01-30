package com.example.homeworkstbc.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.dataStore.UserPrefsRepository
import com.example.yourapp.datastore.UserPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserPrefsRepository
) : ViewModel() {

    val userPrefsFlow: Flow<UserPrefs> = repository.userPrefsFlow

    fun saveUser(firstName: String, lastName: String, email: String) {
        viewModelScope.launch {
            repository.saveUserDetails(firstName, lastName, email)
        }
    }
}