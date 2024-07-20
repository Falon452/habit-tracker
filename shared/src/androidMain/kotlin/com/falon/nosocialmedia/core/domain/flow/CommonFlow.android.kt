package com.falon.nosocialmedia.core.domain.flow

import kotlinx.coroutines.flow.Flow

actual class CommonFlow<T> actual constructor(flow: Flow<T>) : Flow<T> by flow
