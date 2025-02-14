package com.example.homeworkstbc.repository

import ItemsDto
import Resource
import com.example.homeworkstbc.ApiHelper
import com.example.homeworkstbc.client.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService,
    private val apiHelper: ApiHelper
) {
    suspend fun fetchItems(): Resource<List<ItemsDto>> {
        return apiHelper.handleHttpRequest {
            userService.fetchUsers()
        }
    }
}
