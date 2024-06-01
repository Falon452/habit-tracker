package com.falon.nosocialmedia.flow

import kotlinx.coroutines.flow.StateFlow

actual class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>,
) : StateFlow<T>, CommonFlow<T>(stateFlow) {

    override val replayCache: List<T>
        get() = stateFlow.replayCache
    override val value: T
        get() = stateFlow.value
}