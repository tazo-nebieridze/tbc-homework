package com.example.homeworkstbc.data.repositories

import com.example.homeworkstbc.data.Resource
import com.example.homeworkstbc.data.api.ApiHelper
import com.example.homeworkstbc.data.api.LocationService
import com.example.homeworkstbc.data.dtos.LocationDto
import com.example.homeworkstbc.utils.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService,
    private val apiHelper: ApiHelper
) : LocationRepository {
    override suspend fun fetchLocations(): Resource<List<LocationDto>> {
        return apiHelper.handleHttpRequest { locationService.fetchStores() }
    }
}