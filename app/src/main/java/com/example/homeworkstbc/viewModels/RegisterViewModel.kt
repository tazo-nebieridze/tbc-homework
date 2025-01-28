package com.example.homeworkstbc.viewModels

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


    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> get() = _registerState
    fun register(email: String, password: String) {
        _registerState.value = RegisterState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = RegisterRequest(email = email, password = password)
                val response = RetrofitClient.registerService.register(request)

                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success(response.body())
                } else {
                    var errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"

                    if (response.code() == 400) {
                        errorMessage = "Please enter the correct credentials"
                    }

                    _registerState.value = RegisterState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Unexpected error, please try again")
            }
        }
    }
}


sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val data: RegisterDto?) : RegisterState()
    data class Error(val message: String) : RegisterState()
}