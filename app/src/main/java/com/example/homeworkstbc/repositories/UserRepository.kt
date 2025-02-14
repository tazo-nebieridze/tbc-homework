// UserRepository.kt
package com.example.homeworkstbc.repository

import Resource
import com.example.homeworkstbc.ApiHelper
import com.example.homeworkstbc.client.LoginDto
import com.example.homeworkstbc.client.LoginRequest
import com.example.homeworkstbc.client.RegisterDto
import com.example.homeworkstbc.client.RegisterRequest
import com.example.homeworkstbc.client.UserService

import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val ApiHelper: ApiHelper
) {
    suspend fun login(email: String, password: String): Resource<LoginDto> {
        return ApiHelper.handleHttpRequest {
            userService.login(LoginRequest(email = email, password = password))
        }
    }

    suspend fun register(email: String, password: String): Resource<RegisterDto> {
        return ApiHelper.handleHttpRequest {
            userService.register(RegisterRequest(email = email, password = password))
        }
    }
}
