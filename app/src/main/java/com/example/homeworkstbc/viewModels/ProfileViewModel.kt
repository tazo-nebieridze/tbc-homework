// ProfileViewModel.kt
package com.example.homeworkstbc.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val emailFlow: Flow<String?> = dataStoreManager.getEmail()


    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearData()
        }
    }
}
