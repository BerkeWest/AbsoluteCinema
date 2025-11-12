package com.example.absolutecinema.domain.usecase.authentication

import com.example.absolutecinema.data.authentication.AuthRepository
import com.example.absolutecinema.di.IoDispatcher
import com.example.absolutecinema.domain.usecase.generic.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckAccessUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<FlowUseCase.Params, Boolean>(dispatcher) {

    override fun execute(params: Params): Flow<Boolean> {
        return repository.hasAccess()
    }
}