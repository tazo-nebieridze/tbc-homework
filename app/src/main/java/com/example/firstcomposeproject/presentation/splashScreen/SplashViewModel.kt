package com.example.firstcomposeproject.presentation.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.domain.useCase.CheckTokenValidityUseCase
import com.example.firstcomposeproject.domain.useCase.ClearValueUseCase
import com.example.firstcomposeproject.domain.utils.PreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkTokenValidityUseCase: CheckTokenValidityUseCase,
    private val clearValueUseCase: ClearValueUseCase
) : ViewModel() {

    private val _navigationDestination = MutableStateFlow<String?>(null)
    val navigationDestination: StateFlow<String?> = _navigationDestination

    init {
        validateToken()
    }

    private fun validateToken() {
        viewModelScope.launch {
            val isTokenValid = checkTokenValidityUseCase()
            _navigationDestination.value = if (isTokenValid) "main" else "auth"
            if (!isTokenValid) {
                clearValueUseCase(PreferenceKeys.TOKEN)
                clearValueUseCase(PreferenceKeys.EMAIL)
            }
        }
    }
}