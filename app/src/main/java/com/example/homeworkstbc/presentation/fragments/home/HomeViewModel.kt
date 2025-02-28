package com.example.homeworkstbc.presentation.fragments.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.data.Resource
import com.example.homeworkstbc.presentation.mappers.toLocation
import com.example.homeworkstbc.presentation.presentationModules.Location
import com.example.homeworkstbc.utils.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locationsState = MutableStateFlow<Resource<List<Location>>>(Resource.Idle)
    val locationsState: StateFlow<Resource<List<Location>>> = _locationsState

    fun fetchLocations() {
        viewModelScope.launch {
            _locationsState.value = Resource.Loading
            when (val result = locationRepository.fetchLocations()) {
                is Resource.Success -> {
                    val locations = result.data.map { it.toLocation() }
                    _locationsState.value = Resource.Success(locations)
                }
                is Resource.Error -> {
                    _locationsState.value = Resource.Error(result.message)
                }
                else -> Unit
            }
        }
    }
}