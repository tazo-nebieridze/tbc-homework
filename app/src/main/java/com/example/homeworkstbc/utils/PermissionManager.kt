package com.example.homeworkstbc.utils

import com.example.homeworkstbc.data.LocationPermissionStatus

interface PermissionManager {
    fun checkAndRequestLocationPermission(onResult: (LocationPermissionStatus) -> Unit)
}