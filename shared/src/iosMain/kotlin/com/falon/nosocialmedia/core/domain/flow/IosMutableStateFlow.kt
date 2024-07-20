package com.falon.nosocialmedia.core.domain.flow

import com.falon.nosocialmedia.core.domain.flow.CommonMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
    initialValue: T
): CommonMutableStateFlow<T>(MutableStateFlow(initialValue))
