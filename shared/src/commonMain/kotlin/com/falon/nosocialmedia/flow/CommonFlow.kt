package com.falon.nosocialmedia.flow

import kotlinx.coroutines.flow.Flow

expect class CommonFlow<T>(flow: Flow<T>): Flow<T>