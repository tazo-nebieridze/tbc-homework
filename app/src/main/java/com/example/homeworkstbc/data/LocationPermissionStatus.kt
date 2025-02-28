package com.example.homeworkstbc.data

sealed class LocationPermissionStatus {
    data object PermissionGrantedAndLocationEnabled : LocationPermissionStatus()
    data object PermissionGrantedButLocationDisabled : LocationPermissionStatus()
    data object PermissionDenied : LocationPermissionStatus()
}