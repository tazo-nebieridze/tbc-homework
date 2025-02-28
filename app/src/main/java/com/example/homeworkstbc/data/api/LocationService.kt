package com.example.homeworkstbc.data.api

import com.example.homeworkstbc.data.dtos.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {



    @GET("c4c64996-4ed9-4cbc-8986-43c4990d495a")
    suspend fun fetchStores(): Response<List<LocationDto>>
}