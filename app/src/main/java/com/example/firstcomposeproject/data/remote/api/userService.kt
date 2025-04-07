package com.example.firstcomposeproject.data.remote.api

import com.example.firstcomposeproject.data.remote.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<UserResponseDto>
}