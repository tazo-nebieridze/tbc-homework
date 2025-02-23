// StoreMapper.kt
package com.example.homeworkstbc.data.mappers

import com.example.homeworkstbc.data.api.dtos.StoreDto
import com.example.homeworkstbc.domain.entities.Store

object StoreMapper {
    fun fromDto(storeDto: StoreDto): Store {
        return Store(
            id = storeDto.id,
            cover = storeDto.cover,
            title = storeDto.title
        )
    }
}
