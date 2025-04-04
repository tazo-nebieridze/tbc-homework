package com.example.firstcomposeproject.data.utils

import com.example.firstcomposeproject.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ApiHelper @Inject constructor() {
    fun <T> handleHttpRequest(httpCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading(isLoading = true))
        try {
            val response = httpCall.invoke()
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unexpected error, please try again"
                emit(Resource.Error(errorMessage))
            }
            emit(Resource.Loading(isLoading = false))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error, please try again"))
        }
         emit(Resource.Loading(isLoading = false))
    }
}
