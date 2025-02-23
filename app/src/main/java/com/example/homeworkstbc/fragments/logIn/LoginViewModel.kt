// LoginViewModel.kt
package com.example.homeworkstbc.fragments.logIn

import android.util.Log
import com.example.homeworkstbc.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.client.LoginDto
import com.example.homeworkstbc.repositories.UsersRepository
import com.example.homeworkstbc.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<LoginDto>>(Resource.Idle)
    val loginState: StateFlow<Resource<LoginDto>> get() = _loginState


    fun login(email: String, password: String, rememberMe: Boolean) {
        _loginState.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = usersRepository.login(email, password)


            if (result is Resource.Success) {
                val expirationTime = System.currentTimeMillis() + 50 * 60 * 1000

                sessionManager.token = result.data.token
                sessionManager.email = email
                sessionManager.expirationTime = expirationTime
                Log.d("repos","0")

                if (rememberMe) {
                    result.data.token?.let { token ->
                        usersRepository.saveUserAuth(email,token,expirationTime)
                    }
                }
            }
            Log.d("repos","2")
            _loginState.value = result
        }
    }

}
