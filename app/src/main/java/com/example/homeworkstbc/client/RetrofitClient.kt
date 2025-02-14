package com.example.homeworkstbc.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {

    private val contentType = "application/json".toMediaType()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/v3/")
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

}
