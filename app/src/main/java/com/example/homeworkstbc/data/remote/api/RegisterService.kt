package com.example.homeworkstbc.data.remote.api

import com.example.homeworkstbc.data.remote.dto.RegisterDto
import com.example.homeworkstbc.data.remote.dto.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterDto>



}
