package com.example.homeworkstbc.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_remote_keys")
data class UserRemoteKey(
    @PrimaryKey val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
