package com.example.homeworkstbc.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeworkstbc.domain.useCase.RegisterUseCase
import com.example.homeworkstbc.domain.useCase.ValidateEmailUseCase
import com.example.homeworkstbc.domain.useCase.ValidatePasswordUseCase
import com.example.homeworkstbc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()
    private val _sideEffect = MutableSharedFlow<RegisterSideEffect>()
    val sideEffect: SharedFlow<RegisterSideEffect> = _sideEffect.asSharedFlow()

    fun processIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EmailChanged -> {
                val isEmailValid = validateEmailUseCase(intent.email)
                _state.value = _state.value.copy(
                    email = intent.email,
                    isEmailValid = isEmailValid
                )
            }
            is RegisterIntent.PasswordChanged -> {
                val isPasswordValid = validatePasswordUseCase(intent.password)
                val isRepeatPasswordValid = intent.password == _state.value.repeatPassword && isPasswordValid
                _state.value = _state.value.copy(
                    password = intent.password,
                    isPasswordValid = isPasswordValid,
                    isRepeatPasswordValid = isRepeatPasswordValid
                )
            }
            is RegisterIntent.RepeatPasswordChanged -> {
                val isRepeatPasswordValid = _state.value.password == intent.repeatPassword && _state.value.isPasswordValid
                _state.value = _state.value.copy(
                    repeatPassword = intent.repeatPassword,
                    isRepeatPasswordValid = isRepeatPasswordValid
                )
            }
            is RegisterIntent.RegisterClicked -> {
                val currentState = _state.value
                if (currentState.isEmailValid && currentState.isPasswordValid && currentState.isRepeatPasswordValid) {
                    register(currentState.email, currentState.password)
                } else {
                    viewModelScope.launch {
                        _sideEffect.emit(RegisterSideEffect.ShowError("Please fill out the form correctly"))
                    }
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            registerUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = resource.isLoading)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _sideEffect.emit(RegisterSideEffect.NavigateToLogin)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _sideEffect.emit(RegisterSideEffect.ShowError(resource.message))
                    }
                }
            }
        }
    }
}