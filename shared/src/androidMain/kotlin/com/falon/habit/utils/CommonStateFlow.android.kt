package com.falon.habit.utils

import kotlinx.coroutines.flow.StateFlow

actual open class CommonStateFlow<T> actual constructor(stateFlow: StateFlow<T>) :
    StateFlow<T> by stateFlow
