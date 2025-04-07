package com.example.firstcomposeproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.firstcomposeproject.domain.useCase.GetUsersUseCase
import com.example.firstcomposeproject.presentation.home.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        HomeState(
            usersFlow = getUsersUseCase()
                .map { pagingData -> pagingData.map { it.toUiModel() } }
                .cachedIn(viewModelScope)
        )
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()
}