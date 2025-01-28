package com.example.homeworkstbc.viewModels

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

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    val loginState1 = Channel<String>()



    fun startChannel ( ) {
        viewModelScope.launch {
            loginState1.send("error")
        }
    }
    init {
        startChannel()
    }

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
