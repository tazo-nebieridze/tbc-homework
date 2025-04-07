package com.example.firstcomposeproject.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.firstcomposeproject.data.remote.api.UserService
import com.example.firstcomposeproject.data.remote.dto.UserDto
import com.example.firstcomposeproject.data.utils.ApiHelperPaging
import com.example.firstcomposeproject.domain.utils.Resource

class UserPagingSource(
    private val userService: UserService,
    private val apiHelperPaging: ApiHelperPaging
) : PagingSource<Int, UserDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        val page = params.key ?: 1
        val response = apiHelperPaging.handleHttpRequest { userService.getUsers(page) }

        return when (response) {
            is Resource.Success -> {
                val users = response.data.data
                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users.isEmpty()) null else page + 1
                )
            }
            is Resource.Error -> LoadResult.Error(Exception(response.message))
            else -> LoadResult.Error(Exception("Unexpected state"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}