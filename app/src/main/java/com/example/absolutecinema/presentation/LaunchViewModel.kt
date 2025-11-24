package com.example.absolutecinema.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.domain.base.onSuccess
import com.example.absolutecinema.domain.usecase.authentication.CheckAccessUseCase
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject


@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val checkAccessUseCase: CheckAccessUseCase,
) : ViewModel() {

    private val _hasAccess = MutableStateFlow<Boolean?>(null)
    val hasAccess: StateFlow<Boolean?> = _hasAccess

    init {
        performAccessCheck()
    }

    private fun performAccessCheck() {
        try {
            checkAccessUseCase.invoke(FlowUseCase.Params())
                .onSuccess { data ->
                    _hasAccess.value = data
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            _hasAccess.value = false
        }
    }
}