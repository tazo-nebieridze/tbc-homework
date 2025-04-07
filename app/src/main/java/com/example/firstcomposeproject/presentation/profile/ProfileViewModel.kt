package com.example.firstcomposeproject.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.domain.useCase.ClearValueUseCase
import com.example.firstcomposeproject.domain.useCase.ReadValueUseCase
import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val readValueUseCase: ReadValueUseCase,
    private val clearValueUseCase: ClearValueUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<ProfileSideEffect>()
    val sideEffects: Flow<ProfileSideEffect> = _sideEffects.asSharedFlow()

    init {
        processIntent(ProfileIntent.LoadEmail)
    }

    fun processIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadEmail -> loadEmail()
            is ProfileIntent.Logout -> logout()
        }
    }

    private fun loadEmail() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val email = readValueUseCase(PreferenceKeys.EMAIL, "")
                _state.value = _state.value.copy(email = email, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            clearValueUseCase(PreferenceKeys.TOKEN)
            clearValueUseCase(PreferenceKeys.TOKEN_VALIDITY_TIME)
            clearValueUseCase(PreferenceKeys.EMAIL)
            _sideEffects.emit(ProfileSideEffect.NavigateToLogin)
        }
    }
}