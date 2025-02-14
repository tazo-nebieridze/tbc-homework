package com.example.homeworkstbc.client

import UsersDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterDto>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginDto>

    @GET("users")
    suspend fun fetchUsers(@retrofit2.http.Query("page") page: Int): Response<UsersDto>

}
