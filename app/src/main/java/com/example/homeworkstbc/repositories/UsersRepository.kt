// UserRepository.kt
package com.example.homeworkstbc.repositories

import android.util.Log
import com.example.app.DataStoreManager
import com.example.homeworkstbc.utils.Resource
import com.example.homeworkstbc.utils.ApiHelper
import com.example.homeworkstbc.client.LoginDto
import com.example.homeworkstbc.client.LoginRequest
import com.example.homeworkstbc.client.RegisterDto
import com.example.homeworkstbc.client.RegisterRequest
import com.example.homeworkstbc.client.UserService

import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userService: UserService,
    private val apiHelper: ApiHelper,
    private val dataStoreManager: DataStoreManager,

    ) {
    suspend fun login(email: String, password: String): Resource<LoginDto> {
        return apiHelper.handleHttpRequest {
            userService.login(LoginRequest(email = email, password = password))
        }
    }

    suspend fun register(email: String, password: String): Resource<RegisterDto> {
        return apiHelper.handleHttpRequest {
            userService.register(RegisterRequest(email = email, password = password))
        }
    }

    suspend fun saveUserAuth(email: String, token: String, expirationTime: Long) {
        dataStoreManager.saveToken(token, email, expirationTime)
//        delay(2000)
        Log.d("repos", "1")
    }
}