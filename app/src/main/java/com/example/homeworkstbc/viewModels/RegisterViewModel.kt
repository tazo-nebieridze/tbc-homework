package com.example.homeworkstbc.viewModels

import Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RegisterDto
import com.example.homeworkstbc.client.RegisterRequest
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<RegisterDto>>(Resource.Idle)
    val registerState: StateFlow<Resource<RegisterDto>> get() = _registerState

    fun register(email: String, password: String) {
        _registerState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = ApiHelper.handleHttpRequest {
                RetrofitClient.registerService.register(RegisterRequest(email = email, password = password))
            }
            _registerState.value = result
        }
    }
}
