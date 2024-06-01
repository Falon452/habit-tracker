package com.falon.nosocialmedia.flow

import kotlinx.coroutines.flow.StateFlow

expect open class CommonStateFlow<T>(stateFlow: StateFlow<T>): StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() : CommonStateFlow<T> = CommonStateFlow(this)