package com.example.absolutecinema.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.absolutecinema.domain.usecase.authentication.CheckAccessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val checkAccessUseCase: CheckAccessUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashState(null))
    val uiState: StateFlow<SplashState> = _uiState.asStateFlow()

    init {
    }
    /*
        fun access(): Boolean {
            checkAccessUseCase.invoke(FlowUseCase.Params())
                .onSuccess { data ->
                    return if (data) true
                    else false
                }.onError { error ->
                    _uiState.update {
                        it.copy(hasAccess = false)
                    }
                }.launchIn(viewModelScope)
        }
    */
}

data class SplashState(
    val hasAccess: Boolean? = null
)