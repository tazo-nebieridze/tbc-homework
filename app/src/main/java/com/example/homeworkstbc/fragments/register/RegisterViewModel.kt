// RegisterViewModel.kt
package com.example.homeworkstbc.fragments.register

import com.example.homeworkstbc.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RegisterDto
import com.example.homeworkstbc.repositories.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterDto>>(Resource.Idle)
    val registerState: StateFlow<Resource<RegisterDto>> get() = _registerState

    fun register(email: String, password: String) {
        _registerState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = usersRepository.register(email, password)
            _registerState.value = result
        }
    }
}
