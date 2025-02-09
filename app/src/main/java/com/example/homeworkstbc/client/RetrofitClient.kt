package com.example.homeworkstbc.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {

    private val contentType = "application/json".toMediaType()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
        .build()

    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

}
