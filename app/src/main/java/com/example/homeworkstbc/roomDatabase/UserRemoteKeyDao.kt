package com.example.homeworkstbc.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserRemoteKeyDao {
    @Query("SELECT * FROM user_remote_keys WHERE userId = :userId")
    suspend fun remoteKeysByUserId(userId: Int): UserRemoteKey?

    @Query("SELECT * FROM user_remote_keys ORDER BY userId ASC")
    suspend fun remoteKeys(): List<UserRemoteKey>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<UserRemoteKey>)

    @Query("DELETE FROM user_remote_keys")
    suspend fun clearRemoteKeys()
}
