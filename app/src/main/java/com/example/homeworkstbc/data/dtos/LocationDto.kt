package com.example.homeworkstbc.data.dtos


import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val lat: Double,
    val lan: Double,
    val title: String,
    val address: String
)