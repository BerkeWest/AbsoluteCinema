package com.example.absolutecinema.domain.usecase.generic

import com.example.absolutecinema.domain.model.generic.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class FlowUseCase<in P : FlowUseCase.Params, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P) = execute(params).map {
        Result.Success(it) as Result<R>
    }.onStart {
        Result.Loading
    }.catch {
        Result.Error(it)
    }.flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>

    open class Params
}
