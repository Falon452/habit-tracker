package com.falon.habit.utils

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

actual open class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>,
) : StateFlow<T> {

    override val replayCache: List<T>
        get() = stateFlow.replayCache
    override val value: T
        get() = stateFlow.value

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        TODO()
    }
}
