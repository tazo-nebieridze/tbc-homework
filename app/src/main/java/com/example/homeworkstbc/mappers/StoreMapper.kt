// StoreMapper.kt
package com.example.homeworkstbc.mappers

import StoreDto
import com.example.homeworkstbc.domain.Store

object StoreMapper {
    fun fromDto(storeDto: StoreDto): Store {
        return Store(
            id = storeDto.id,
            cover = storeDto.cover,
            title = storeDto.title
        )
    }
}
