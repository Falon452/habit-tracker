package com.falon.nosocialmedia.flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

actual class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow {

    fun subscribe(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit,
    ): DisposableHandle {
        val job = coroutineScope.launch(dispatcher) {
            flow.collect { value ->
                onCollect(value)
            }
        }
        return DisposableHandle {
            job.cancel()
        }
    }
}