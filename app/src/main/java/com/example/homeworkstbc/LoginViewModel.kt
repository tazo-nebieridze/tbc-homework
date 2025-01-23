package com.example.homeworkstbc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        _loginState.postValue(LoginState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = LoginRequest(email = email, password = password)
                val response = RetrofitClient.registerService.login(request)

                if (response.isSuccessful) {
                    _loginState.postValue(LoginState.Success(response.body()))
                } else {
                    var errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"

                    if (response.code() == 400) {
                        errorMessage = "user not found"
                    }

                    _loginState.postValue(LoginState.Error(errorMessage))
                }
            } catch (e: Exception) {
                _loginState.postValue(LoginState.Error("Unexpected error, please try again"))
            }
        }
    }
}


sealed class LoginState {
    object Loading : LoginState()
    data class Success(val data: LoginDto?) : LoginState()
    data class Error(val message: String) : LoginState()
}
