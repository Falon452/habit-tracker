package com.falon.habit.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
actual open class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T> {

    actual override var value: T
        get() = flow.value
        set(value) {
            flow.value = value
        }

    actual override val replayCache: List<T>
        get() = flow.replayCache

    actual override suspend fun emit(value: T) = flow.emit(value)

    actual override fun tryEmit(value: T): Boolean = flow.tryEmit(value)

    actual override suspend fun collect(collector: FlowCollector<T>): Nothing = flow.collect(collector)

    @ExperimentalCoroutinesApi
    actual override fun resetReplayCache() = flow.resetReplayCache()

    actual override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    actual override fun compareAndSet(expect: T, update: T): Boolean = (flow as? MutableStateFlow<T>)?.compareAndSet(expect, update) == true
}
