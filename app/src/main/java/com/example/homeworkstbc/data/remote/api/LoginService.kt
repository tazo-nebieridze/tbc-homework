package com.example.homeworkstbc.data.remote.api

import com.example.homeworkstbc.data.remote.dto.LoginDto
import com.example.homeworkstbc.data.remote.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {


    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginDto>


}