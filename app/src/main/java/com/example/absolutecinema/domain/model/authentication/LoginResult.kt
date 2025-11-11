package com.example.absolutecinema.domain.model.authentication

sealed class LoginResult {
    object Idle : LoginResult()
    object Loading : LoginResult()
    data class Success(val success: Boolean) : LoginResult()
    data class Error(val message: String?) : LoginResult()
}
