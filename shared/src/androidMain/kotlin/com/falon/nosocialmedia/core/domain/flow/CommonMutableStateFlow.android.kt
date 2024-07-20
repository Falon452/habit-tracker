package com.falon.nosocialmedia.core.domain.flow

import kotlinx.coroutines.flow.MutableStateFlow

actual open class CommonMutableStateFlow<T> actual constructor(flow: MutableStateFlow<T>) :
    MutableStateFlow<T> by flow
