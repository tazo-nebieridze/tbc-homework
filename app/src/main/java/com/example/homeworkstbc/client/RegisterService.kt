package com.example.homeworkstbc.client

import com.example.homeworkstbc.dto.UsersDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RegisterService {

    @GET("v3/f3f41821-7434-471f-9baa-ae3dee984e6d")
    suspend fun getUsers(): Response<UsersDto>


}

