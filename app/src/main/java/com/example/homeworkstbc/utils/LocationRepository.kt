package com.example.homeworkstbc.utils

import com.example.homeworkstbc.data.Resource
import com.example.homeworkstbc.data.dtos.LocationDto

interface LocationRepository {
    suspend fun fetchLocations(): Resource<List<LocationDto>>
}