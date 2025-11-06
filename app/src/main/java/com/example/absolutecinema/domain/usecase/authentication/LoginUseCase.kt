package com.example.absolutecinema.domain.usecase.authentication

import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.domain.model.authentication.LoginResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(username: String, password: String) = flow {
        emit(LoginResult.Loading)
        if (username.isBlank() || password.isBlank()) {
            emit(LoginResult.Error("Username and password cannot be empty"))
            return@flow
        }
        try {
            val result = authRepository.login(username, password)
            emit(LoginResult.Success(result))
        } catch (e: Exception) {
            emit(LoginResult.Error("An error occurred"))
        }
    }
}