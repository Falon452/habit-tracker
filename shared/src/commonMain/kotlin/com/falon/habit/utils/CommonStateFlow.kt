package com.falon.habit.utils

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

expect open class CommonStateFlow<T>(stateFlow: StateFlow<T>) : StateFlow<T> {
    override val value: T
    override suspend fun collect(collector: FlowCollector<T>): Nothing
    override val replayCache: List<T>
}

fun <T> StateFlow<T>.toCommonStateFlow() : CommonStateFlow<T> = CommonStateFlow(this)
