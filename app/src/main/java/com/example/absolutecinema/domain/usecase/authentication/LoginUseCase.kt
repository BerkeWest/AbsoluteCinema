package com.example.absolutecinema.domain.usecase.authentication

import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.model.authentication.LoginResult
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<LoginUseCase.Params, LoginResult>(dispatcher) {

    override fun execute(params: Params): Flow<LoginResult> = flow{
        if (params.username.isBlank() || params.password.isBlank()) emit(LoginResult.Error("Username and password cannot be empty"))
        else {
            emit(LoginResult.Loading)
            try {
                val result = authRepository.login(params.username, params.password)
                emit(LoginResult.Success(result))
            } catch (e: Exception) {
                emit(LoginResult.Error("An error occurred"))
            }
        }
    }

    data class Params(
        val username: String,
        val password: String
    ) : FlowUseCase.Params()
}