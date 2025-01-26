package com.example.homeworkstbc.client

import UsersDto
import com.example.homeworkstbc.LoginDto
import com.example.homeworkstbc.LoginRequest
import com.example.homeworkstbc.RegisterDto
import com.example.homeworkstbc.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterDto>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginDto>

    @GET("users?page=1")
    suspend fun fetchUsers(): Response<UsersDto>
}