package com.example.homeworkstbc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState
    fun login(email: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = LoginRequest(email = email, password = password)
                val response = RetrofitClient.registerService.login(request)

                if (response.isSuccessful) {
                    _loginState.value = LoginState.Success(response.body())
                } else {
                    var errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"

                    if (response.code() == 400) {
                        errorMessage = "user not found"
                    }

                    _loginState.value = LoginState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Unexpected error, please try again")
            }
        }
    }
}


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginDto?) : LoginState()
    data class Error(val message: String) : LoginState()
}
