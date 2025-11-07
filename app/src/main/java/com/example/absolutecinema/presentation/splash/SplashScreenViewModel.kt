package com.example.absolutecinema.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.domain.usecase.authentication.CheckAccessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val checkAccessUseCase: CheckAccessUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(SplashState(null))
    val uiState: StateFlow<SplashState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val hasAccess = checkAccessUseCase()
            _uiState.update { it.copy(hasAccess = hasAccess) }
        }
    }
}

data class SplashState(
    val hasAccess: Boolean? = null
)