package com.example.homeworkstbc.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.domain.useCase.FetchCategoryUseCase
import com.example.homeworkstbc.domain.useCase.FilterCategoriesUseCase
import com.example.homeworkstbc.domain.utils.Resource
import com.example.homeworkstbc.presentation.mappers.toPresentation
import com.example.homeworkstbc.presentation.mappers.toDomain
import com.example.homeworkstbc.presentation.models.Category
import com.example.homeworkstbc.presentation.utils.flatten
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCategoryUseCase: FetchCategoryUseCase,
    private  val filterCategoriesUseCase:FilterCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()
    private var originalCategories: List<Category> = emptyList()
    private var searchJob: Job? = null
    init {
        handleIntent(HomeIntent.FetchCategories)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchCategories -> fetchCategories()
            is HomeIntent.SearchCategories -> searchCategories(intent.query)
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            fetchCategoryUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val categories = resource.data.map { it.toPresentation() }.flatten()
                        originalCategories = categories
                        _state.value = _state.value.copy(
                            isLoading = false,
                            categories = categories
                        )
                        Log.d("HomeViewModel", "Success: isLoading=false, Categories size: ${categories.size}")
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                        _sideEffect.emit(HomeSideEffect.ShowError(resource.message ?: "Unknown error"))
                        Log.e("HomeViewModel", "Error: isLoading=false, Message: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                        Log.d("HomeViewModel", "Loading: isLoading=true")
                    }
                }
            }
        }
    }


    private fun searchCategories(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(2000)
            val filteredCategories = if (query.isBlank()) {
                originalCategories
            } else {
                filterCategoriesUseCase(
                    originalCategories.map { it.toDomain() },
                    query
                ).map { it.toPresentation() }
            }
            _state.value = _state.value.copy(categories = filteredCategories)
            Log.d("HomeViewModel", "Search: query='$query', Filtered size: ${filteredCategories.size}")
        }
    }
}

