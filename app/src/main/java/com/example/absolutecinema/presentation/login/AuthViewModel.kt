package com.example.absolutecinema.presentation.login

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.R
import com.example.absolutecinema.data.authentication.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState = _loginState.asStateFlow()

    private val _passwordState = MutableStateFlow(
        PasswordState(
            visible = false,
            icon = R.drawable.visibility_on,
            description = "Show password",
            visualTransformation = PasswordVisualTransformation()
        )
    )
    val passwordState: StateFlow<PasswordState> = _passwordState.asStateFlow()

    fun togglePasswordVisibility() {
        if (_passwordState.value.visible) {
            _passwordState.update {
                it.copy(
                    visible = false,
                    icon = R.drawable.visibility_on,
                    description = "Show password",
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        } else {
            _passwordState.update {
                it.copy(
                    visible = true,
                    icon = R.drawable.visibility_off,
                    description = "Hide password",
                    visualTransformation = VisualTransformation.None
                )
            }
        }
    }


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

data class PasswordState(
    var visible: Boolean = false,
    var icon: Int = 0,
    var description: String = "",
    var visualTransformation: VisualTransformation = VisualTransformation.None
)