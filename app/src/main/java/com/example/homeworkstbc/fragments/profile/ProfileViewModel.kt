// ProfileViewModel.kt
package com.example.homeworkstbc.fragments.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app.DataStoreManager
import com.example.homeworkstbc.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val emailFlow: Flow<String?> = flow {
        emit(sessionManager.email)
    }

    suspend fun logout() {
        dataStoreManager.clearData()
        sessionManager.clearSession()
//        delay(2000)
        Log.d("profile","1")
    }
}
