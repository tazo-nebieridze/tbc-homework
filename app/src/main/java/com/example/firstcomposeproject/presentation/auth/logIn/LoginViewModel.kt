package com.example.firstcomposeproject.presentation.auth.logIn

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.domain.useCase.LoginUseCase
import com.example.firstcomposeproject.domain.useCase.SaveTokenUseCase
import com.example.firstcomposeproject.domain.useCase.SaveValueUseCase
import com.example.firstcomposeproject.domain.useCase.ValidateEmailUseCase
import com.example.firstcomposeproject.domain.useCase.ValidatePasswordUseCase
import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import com.example.firstcomposeproject.domain.utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveValueUseCase: SaveValueUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect: SharedFlow<LoginSideEffect> = _sideEffect.asSharedFlow()

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                val isEmailValid = validateEmailUseCase(intent.email)
                _state.value = _state.value.copy(
                    email = intent.email,
                    isEmailValid = isEmailValid
                )
            }
            is LoginIntent.PasswordChanged -> {
                val isPasswordValid = validatePasswordUseCase(intent.password)
                _state.value = _state.value.copy(
                    password = intent.password,
                    isPasswordValid = isPasswordValid
                )
            }
            is LoginIntent.RememberMeToggled -> {
                _state.value = _state.value.copy(rememberMe = intent.isChecked)
            }
            is LoginIntent.LoginClicked -> {
                Log.d("loginViewModel","clicked")
                val currentState = _state.value
                if (currentState.isEmailValid && currentState.isPasswordValid) {

                    login(currentState.email, currentState.password, currentState.rememberMe)
                } else {
//                    Log.d("loginViewModel","error")

                    viewModelScope.launch {
                        _sideEffect.emit(LoginSideEffect.ShowError("Please fill out the form correctly"))
                    }
                }
            }
        }
    }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            loginUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = resource.isLoading)
                        Log.d("loginViewModel","isLoading")
                    }
                    is Resource.Success -> {
                        saveTokenUseCase(resource.data.token!!, rememberMe)
                        saveValueUseCase(PreferenceKeys.EMAIL, state.value.email)
                        _state.value = _state.value.copy(isLoading = false)
                        _sideEffect.emit(LoginSideEffect.NavigateToHome)
                        Log.d("loginViewModel","Success")
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _sideEffect.emit(LoginSideEffect.ShowError(resource.message))
                        Log.d("loginViewModel","Error")
                    }
                }
            }
        }
    }
}