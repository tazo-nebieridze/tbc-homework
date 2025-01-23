package com.example.homeworkstbc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> get() = _registerState

    fun register(email: String, password: String) {
        _registerState.postValue(RegisterState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = RegisterRequest(email = email, password = password)
                val response = RetrofitClient.registerService.register(request)

                if (response.isSuccessful) {
                    _registerState.postValue(RegisterState.Success(response.body()))
                } else {
                    var errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"

                    if (response.code() == 400) {
                        errorMessage = "Please enter the correct credentials"
                    }

                    _registerState.postValue(RegisterState.Error(errorMessage))
                }
            } catch (e: Exception) {
                _registerState.postValue(RegisterState.Error("Unexpected error, please try again"))
            }
        }
    }
}


sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val data: RegisterDto?) : RegisterState()
    data class Error(val message: String) : RegisterState()
}