// com.example.homeworkstbc.fragments.home/HomeViewModel.kt
package com.example.homeworkstbc.presentation.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.data.repositories.UsersRepository

import com.example.homeworkstbc.domain.entities.Post
import com.example.homeworkstbc.domain.entities.Store
import com.example.homeworkstbc.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {

    private val _storesState = MutableStateFlow<Resource<List<Store>>>(Resource.Idle)
    val storesState: StateFlow<Resource<List<Store>>> = _storesState.asStateFlow()

    private val _postsState = MutableStateFlow<Resource<List<Post>>>(Resource.Idle)
    val postsState: StateFlow<Resource<List<Post>>> = _postsState.asStateFlow()

    init {
        fetchStores()
        fetchPosts()
    }

    private fun fetchStores() {
        viewModelScope.launch {
            _storesState.value = Resource.Loading
            val result = usersRepository.getStores()
            _storesState.value = result
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _postsState.value = Resource.Loading
            val result = usersRepository.getPosts()
            _postsState.value = result
        }
    }
}