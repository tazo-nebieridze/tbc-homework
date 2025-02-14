package com.example.homeworkstbc.roomDatabase

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserDb ORDER BY id ASC")
    fun getPagingSource(): PagingSource<Int, UserDb>

    @Query("SELECT * FROM UserDb")
    fun getAll(): Flow<List<UserDb>>

    @Query("SELECT * FROM UserDb WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserDb>

    @Query("SELECT * FROM UserDb WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): UserDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: UserDb)

    @Delete
    suspend fun delete(user: UserDb)

    @Query("DELETE FROM UserDb")
    suspend fun clearAll()
}
