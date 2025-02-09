package com.example.homeworkstbc.viewModels

import Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.LoginDto
import com.example.homeworkstbc.client.LoginRequest
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<LoginDto>>(Resource.Idle)
    val loginState: StateFlow<Resource<LoginDto>> get() = _loginState

    fun login(email: String, password: String) {
        _loginState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = ApiHelper.handleHttpRequest {
                RetrofitClient.registerService.login(LoginRequest(email = email, password = password))
            }
            _loginState.value = result
        }
    }
}

