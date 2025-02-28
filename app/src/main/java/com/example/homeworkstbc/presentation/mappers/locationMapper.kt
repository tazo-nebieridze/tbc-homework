package com.example.homeworkstbc.presentation.mappers

import com.example.homeworkstbc.data.dtos.LocationDto
import com.example.homeworkstbc.presentation.presentationModules.Location

fun LocationDto.toLocation(): Location {
    return Location(
        lat = this.lat,
        lng = this.lan,
        title = this.title,
        address = this.address
    )
}