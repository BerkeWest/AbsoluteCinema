package com.example.absolutecinema.domain.usecase.authentication

import com.example.absolutecinema.data.authentication.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckAccessUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = flow {
        emit(authRepository.hasAccess())
    }
}