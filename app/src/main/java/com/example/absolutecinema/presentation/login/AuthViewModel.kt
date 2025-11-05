package com.example.absolutecinema.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.data.authentication.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState = _loginState.asStateFlow()

    init {
        viewModelScope.launch {
            if (repository.hasAccess()) {
                _loginState.value = true
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                if (username.isNotBlank() && password.isNotBlank()) {
                    val result = repository.login(username, password)
                    _loginState.value = result
                } else {
                    _loginState.value = false
                }
            } catch (e: Exception) {
                _loginState.value = false
            }
        }
    }

    fun onLoginStateConsumed() {
        _loginState.value = null
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _loginState.value = null
        }
    }
}