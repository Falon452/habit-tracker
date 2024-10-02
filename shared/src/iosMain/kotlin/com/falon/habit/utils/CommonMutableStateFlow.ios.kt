package com.falon.habit.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual open class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T>, CommonStateFlow<T>(flow) {

    override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    override suspend fun emit(value: T) {
        flow.emit(value)
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        flow.resetReplayCache()
    }

    override fun tryEmit(value: T): Boolean =
        flow.tryEmit(value)

    override fun compareAndSet(expect: T, update: T): Boolean =
        flow.compareAndSet(expect, update)
}
