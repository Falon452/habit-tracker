package com.falon.habit.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
actual open class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T>, CommonStateFlow<T>(flow) {

    actual override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    actual override suspend fun emit(value: T) {
        flow.emit(value)
    }

    @ExperimentalCoroutinesApi
    actual override fun resetReplayCache() {
        flow.resetReplayCache()
    }

    actual override fun tryEmit(value: T): Boolean =
        flow.tryEmit(value)

    actual override fun compareAndSet(expect: T, update: T): Boolean =
        flow.compareAndSet(expect, update)
}
