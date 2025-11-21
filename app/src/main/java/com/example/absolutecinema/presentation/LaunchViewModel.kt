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
        // Run the check as soon as the ViewModel is created
        performAccessCheck()
    }

    private fun performAccessCheck() {
        // The .first() terminal operator will suspend until one value is emitted
        // and then return it. This elegantly handles the async nature.
        // We wrap in a try-catch to handle potential errors from the use case.
        try {
            checkAccessUseCase.invoke(FlowUseCase.Params())
                .onSuccess { data ->
                    _hasAccess.value = data
                }.launchIn(viewModelScope)
        } catch (e: Exception) {
            // If any error occurs during the flow, treat it as no access.
            _hasAccess.value = false
        }
    }
}