package com.falon.habit.utils

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
    initialValue: T
): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))
