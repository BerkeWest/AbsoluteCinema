package com.example.absolutecinema.presentation.login

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absolutecinema.R
import com.example.absolutecinema.domain.base.onError
import com.example.absolutecinema.domain.base.onSuccess
import com.example.absolutecinema.domain.model.authentication.LoginResult
import com.example.absolutecinema.domain.usecase.authentication.LoginUseCase
import com.example.absolutecinema.domain.usecase.authentication.LogoutUseCase
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import com.example.absolutecinema.presentation.utils.PassWordVisibility
import com.example.absolutecinema.presentation.utils.PasswordState
import com.example.absolutecinema.presentation.utils.notifications.Notification
import com.example.absolutecinema.presentation.utils.notifications.NotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenUIState())
    val uiState = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        loginUseCase.invoke(LoginUseCase.Params(username, password))
            .onSuccess { data ->
                _uiState.update { it.copy(loginState = data) }
                if (data is LoginResult.Success) {
                    NotificationManager.show {
                        Notification(
                            message = stringResource(R.string.welcome_message),
                            icon = R.drawable.visibility_off,
                            onClick = { /* navigate */ },
                            onDismiss = { NotificationManager.dismiss() }
                        )
                    }
                }
            }.onError { error ->
                _uiState.update { it.copy(loginState = LoginResult.Error(error.localizedMessage)) }
            }.launchIn(viewModelScope)
    }

    fun logout() {
        logoutUseCase(FlowUseCase.Params()).launchIn(viewModelScope)
    }

    fun onLoginStateConsumed() {
        _uiState.update { it.copy(loginState = LoginResult.Idle) }
    }

    fun onUsernameChange(text: String) {
        _uiState.update { currentState ->
            currentState.copy(username = text, isError = false)
        }
    }

    fun onPasswordChange(text: String) {
        _uiState.update { currentState ->
            currentState.copy(password = text, isError = false)
        }
    }

    fun togglePasswordVisibility() {
        if (_uiState.value.passwordState.visible) {
            _uiState.update {
                it.copy(
                    passwordState =
                        PassWordVisibility.INVISIBLE.state
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    passwordState =
                        PassWordVisibility.VISIBLE.state
                )
            }
        }
    }
}

data class LoginScreenUIState(
    var loginState: LoginResult = LoginResult.Idle,
    var username: String = "",
    var password: String = "",
    var isError: Boolean = false,
    var passwordState: PasswordState = PasswordState()
)