package com.falon.habit.utils

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
actual open class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>
) : StateFlow<T> {

    actual override val value: T
        get() = stateFlow.value

    actual override suspend fun collect(collector: FlowCollector<T>) =
        stateFlow.collect(collector)

    actual override val replayCache: List<T>
        get() = stateFlow.replayCache
}
