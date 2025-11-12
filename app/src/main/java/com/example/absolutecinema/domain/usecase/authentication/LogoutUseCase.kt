package com.example.absolutecinema.domain.usecase.authentication

import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<FlowUseCase.Params, Unit>(dispatcher) {

    override fun execute(params: Params): Flow<Unit> = authRepository.logout()

}