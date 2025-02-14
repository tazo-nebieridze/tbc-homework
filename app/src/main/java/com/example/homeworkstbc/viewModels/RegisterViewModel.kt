// RegisterViewModel.kt
package com.example.homeworkstbc.viewModels

import Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RegisterDto
import com.example.homeworkstbc.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterDto>>(Resource.Idle)
    val registerState: StateFlow<Resource<RegisterDto>> get() = _registerState

    fun register(email: String, password: String) {
        _registerState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.register(email, password)
            _registerState.value = result
        }
    }
}
