package com.example.homeworkstbc.roomDatabase

import UsersDto
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import androidx.paging.PagingState
import com.example.homeworkstbc.client.UserService
import androidx.room.withTransaction

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: AppDatabase,
    private val service: UserService,
    private val isNetworkAvailable: () -> Boolean
) : RemoteMediator<Int, UserDb>() {

    private suspend fun getLastRemoteKey(state: PagingState<Int, UserDb>): UserRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { user ->
            database.userRemoteKeyDao().remoteKeysByUserId(user.id)
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserDb>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("UserRemoteMediator", "REFRESH load, starting at page 1")
                    1
                }
                LoadType.PREPEND -> {
                    Log.d("UserRemoteMediator", "REFRESH load, forcing page 1")
                    1
                }
                LoadType.APPEND -> {
                    val remoteKey = getLastRemoteKey(state)
                    if (remoteKey?.nextKey == null) {
                        Log.d("UserRemoteMediator", "No nextKey, end of pagination reached")
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }

            if (!isNetworkAvailable()) {
                Log.d("UserRemoteMediator", "Offline mode: no network available, using cached data")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            Log.d("UserRemoteMediator", "Fetching API page: $page")
            val response = service.fetchUsers(page)
            if (response.isSuccessful) {
                val usersDto: UsersDto? = response.body()
                if (usersDto == null) {
                    Log.d("UserRemoteMediator", "No data received; end of pagination")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                val endOfPaginationReached = page >= usersDto.totalPages

                val usersDbList = usersDto.data.map { user ->
                    UserDb(
                        id = user.id,
                        email = user.email,
                        avatar = user.avatar,
                        firstName = user.firstName,
                        lastName = user.lastName
                    )
                }


                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = usersDto.data.map { user ->
                    UserRemoteKey(
                        userId = user.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = nextKey
                    )
                }

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.userDao().clearAll()
                        database.userRemoteKeyDao().clearRemoteKeys()
                    }
                    database.userDao().insertAll(*usersDbList.toTypedArray())
                    database.userRemoteKeyDao().insertAll(keys)
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                Log.e("UserRemoteMediator", "Error fetching page: ${response.message()}")
                return MediatorResult.Error(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("UserRemoteMediator", "Exception during load", e)
            return MediatorResult.Error(e)
        }
    }
}
