// UsersRepository.kt
package com.example.homeworkstbc.repositories

import Post
import com.example.homeworkstbc.client.UserService
import com.example.homeworkstbc.domain.Store
import com.example.homeworkstbc.mappers.StoreMapper
import com.example.homeworkstbc.utils.ApiHelper
import com.example.homeworkstbc.utils.Resource
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userService: UserService,
    private val apiHelper: ApiHelper
) {
    suspend fun getStores(): Resource<List<Store>> {
        val result = apiHelper.handleHttpRequest { userService.fetchStores() }
        return when (result) {
            is Resource.Success -> {
                // Map each StoreDto to a Store
                val stores = result.data.map { StoreMapper.fromDto(it) }
                Resource.Success(stores)
            }
            is Resource.Error -> result
            else -> Resource.Error("Unknown error")
        }
    }
    suspend fun getPosts(): Resource<List<Post>> {
        val result = apiHelper.handleHttpRequest { userService.fetchPosts() }
        return when (result) {
            is Resource.Success -> {
                val posts = result.data.map { PostMapper.mapToPresentation(it) }
                Resource.Success(posts)
            }
            is Resource.Error -> result
            else -> Resource.Error("Unknown error")
        }
    }
}
