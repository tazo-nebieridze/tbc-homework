package com.example.homeworkstbc.data.api

import PostsDto
import com.example.homeworkstbc.data.api.dtos.StoreDto
import retrofit2.Response
import retrofit2.http.GET

interface UserService {


    @GET("1ba8b612-8391-41e5-8560-98e4a48decc7")
    suspend fun fetchPosts(): Response<List<PostsDto>>

    @GET("00a18030-a8c7-47c4-b0c5-8bff92a29ebf")
    suspend fun fetchStores(): Response<List<StoreDto>>
}