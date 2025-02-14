package com.example.homeworkstbc.client

import ItemsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {


     @GET("6dffd14a-836f-4566-b024-bd41ace3a874")
        suspend fun fetchUsers(): Response<List<ItemsDto>>

}
