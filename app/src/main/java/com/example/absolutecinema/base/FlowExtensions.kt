package com.example.absolutecinema.base

import com.example.absolutecinema.domain.model.generic.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


fun <T> Flow<Result<T>>.onLoading(action: suspend () -> Unit) = onEach {
    if (it is Result.Loading) {
        action()
    }
}

fun <T> Flow<Result<T>>.onSuccess(action: suspend (T) -> Unit) = onEach {
    if (it is Result.Success) {
        action(it.data)
    }
}

fun <T> Flow<Result<T>>.onError(action: suspend (Throwable) -> Unit) = onEach {
    if (it is Result.Error) {
        action(it.exception)
    }
}