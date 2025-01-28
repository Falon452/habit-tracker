package com.falon.habit.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.ExperimentalCoroutinesApi

expect open class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T> {
    override var value: T
    override val replayCache: List<T>
    override suspend fun emit(value: T): Unit
    override fun tryEmit(value: T): Boolean
    override suspend fun collect(collector: FlowCollector<T>): Nothing

    @ExperimentalCoroutinesApi
    override fun resetReplayCache(): Unit

    override val subscriptionCount: StateFlow<Int>
    override fun compareAndSet(expect: T, update: T): Boolean
}
fun <T> MutableStateFlow<T>.toCommonMutableStateFlow() = CommonMutableStateFlow(this)
