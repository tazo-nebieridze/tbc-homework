// LoginViewModel.kt
package com.example.homeworkstbc.viewModels

import Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.LoginDto
import com.example.homeworkstbc.repository.UserRepository
import com.example.app.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<LoginDto>>(Resource.Idle)
    val loginState: StateFlow<Resource<LoginDto>> get() = _loginState


    fun login(email: String, password: String, rememberMe: Boolean) {
        _loginState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = userRepository.login(email, password)
            _loginState.value = result

            if (result is Resource.Success && rememberMe) {
                result.data?.token?.let { token ->
                    val expirationTime = System.currentTimeMillis() + 50 * 60 * 1000
                    dataStoreManager.saveToken(token, email, expirationTime)
                }
            }
        }
    }
}
