package com.falon.habit.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

expect class CommonFlow<T>(flow: Flow<T>) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>)
}

fun <T> Flow<T>.toCommonFlow() = CommonFlow(this)
